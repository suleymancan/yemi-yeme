package com.suleymancanblog.yemiyeme;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.LogisticRegressionModel;
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import scala.Tuple2;

/**
 * Created on April, 2019
 *
 * @author suleymancan
 */
public class YemiYemeModelGenerator {

	private static final String APP_NAME = "YemiYeme";

	private static final String MASTER = "local";

	private static final String PATH = "/home/suleymancan/dev/yemi-yeme/src/main/resources/static/yemi-yeme-dataset.txt";

	private static final String MODEL_PATH = "/home/suleymancan/dev/yemi-yeme/src/main/resources/static/model/YemiYemeModel";

	public static void main(String[] args) {
		final SparkConf sparkConf = new SparkConf().setAppName(APP_NAME).setMaster(MASTER);
		final SparkContext sparkContext = new SparkContext(sparkConf);
		final JavaSparkContext javaSparkContext = new JavaSparkContext(sparkContext);

		final JavaRDD<String> datasetFile = javaSparkContext.textFile(PATH, 1);


		final JavaRDD<LabeledPoint> data = datasetFile.map(line -> {
			final String[] split = line.split(",");
			final double label = Double.valueOf(split[split.length - 1]);
			final double[] doubleFeatures = new double[split.length-1];
			for(int i = 0; i < split.length-1; i++){
				doubleFeatures[i] = Double.valueOf(split[i]);
			}
			final Vector features  = Vectors.dense(doubleFeatures);
			return new LabeledPoint(label, features);
		});

		final JavaRDD<LabeledPoint>[] splits = data.randomSplit(new double[] { 0.7, 0.3 });
		final JavaRDD<LabeledPoint> training = splits[0].cache();
		final JavaRDD<LabeledPoint> test = splits[1];


		final LogisticRegressionModel model = new LogisticRegressionWithLBFGS().setNumClasses(2).run(training.rdd());


		final JavaRDD<Tuple2<Object, Object>> predictionAndLabels = test.map(p -> {
			Double prediction = model.predict(p.features());
			return new Tuple2<>(prediction, p.label());
		});

		final MulticlassMetrics metrics = new MulticlassMetrics(predictionAndLabels.rdd());
		System.out.println("accuracy" + metrics.accuracy());

		final String modelLink = MODEL_PATH;
		model.save(sparkContext, modelLink);

		sparkContext.stop();
		javaSparkContext.stop();

	}

}
