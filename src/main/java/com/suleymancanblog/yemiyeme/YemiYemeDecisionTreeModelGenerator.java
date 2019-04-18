package com.suleymancanblog.yemiyeme;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import scala.Tuple2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on April, 2019
 *
 * @author suleymancan
 */
public class YemiYemeDecisionTreeModelGenerator {

	private static final String APP_NAME = "YemiYeme";

	private static final String MASTER = "local";

	private static final String DATA_PATH = "/home/suleymancan/dev/yemi-yeme/src/main/resources/static/yemi-yeme-prizma-dataset.txt";

	private static final String MODEL_SAVE_PATH = "/home/suleymancan/dev/yemi-yeme/src/main/resources/static/model/prizma/YemiYemeModel";

	public static void main(String[] args) {
		final SparkConf sparkConf = new SparkConf().setAppName(APP_NAME).setMaster(MASTER);
		final JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);

		final JavaRDD<String> stringJavaRDD = javaSparkContext.textFile(DATA_PATH, 1);

		// String data convert to LabeledPoint
		// https://github.com/bhdrkn/Java-Examples/blob/master/ml-spark-service/src/main/java/com/bahadirakin/ml/QualitativeBankruptcyModelGenerator.java
		final JavaRDD<LabeledPoint> labeledPointJavaRDD = stringJavaRDD.map(line -> {
			final String[] split = line.split(",");
			final double label = Double.valueOf(split[split.length - 1]);
			final double[] doubleFeatures = new double[split.length - 1];
			for (int i = 0; i < split.length - 1; i++) {
				doubleFeatures[i] = Double.valueOf(split[i]);
			}
			final Vector features = Vectors.dense(doubleFeatures);
			return new LabeledPoint(label, features);
		});

		// Split the data into training and test sets (30% held out for testing)
		final JavaRDD<LabeledPoint>[] splits = labeledPointJavaRDD.randomSplit(new double[] { 0.7, 0.3 });
		final JavaRDD<LabeledPoint> trainData = splits[0];
		final JavaRDD<LabeledPoint> testData = splits[1];

		// Set parameters.
		// https://spark.apache.org/docs/2.2.0/api/java/org/apache/spark/mllib/tree/DecisionTree.html#trainClassifier-org.apache.spark.rdd.RDD-int-scala.collection.immutable.Map-java.lang.String-int-int-
		final int numClasses = 2;
		final Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<>();
		final String impurity = "gini";
		final int maxDepth = 5;
		final int maxBins = 32;

		final DecisionTreeModel model = DecisionTree.trainClassifier(trainData, numClasses, categoricalFeaturesInfo, impurity, maxDepth, maxBins);

		// Evaluate model on test instances and compute test error
		// https://spark.apache.org/docs/2.2.0/mllib-decision-tree.html
		final JavaPairRDD<Double, Double> predictionAndLabels = testData.mapToPair(p -> new Tuple2<>(model.predict(p.features()), p.label()));
		final double testErr = predictionAndLabels.filter(pl -> !pl._1().equals(pl._2())).count() / (double) testData.count();

		System.out.println("Test Error: " + testErr);

		model.save(javaSparkContext.sc(), MODEL_SAVE_PATH);
		javaSparkContext.stop();

	}

}
