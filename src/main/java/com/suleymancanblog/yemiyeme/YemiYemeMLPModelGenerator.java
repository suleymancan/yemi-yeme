package com.suleymancanblog.yemiyeme;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel;
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier;
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator;
import org.apache.spark.ml.feature.LabeledPoint;
import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * Created on April, 2019
 *
 * @author suleymancan
 */
public class YemiYemeMLPModelGenerator {

	private static final String APP_NAME = "YemiYeme";

	private static final String MASTER = "local";

	private static final String PATH = "/home/suleymancan/dev/yemi-yeme/src/main/resources/static/yemi-yeme-dataset.txt";

	private static final String MODEL_PATH = "/home/suleymancan/dev/yemi-yeme/src/main/resources/static/model/YemiYemeModel";

	public static void main(String[] args) {
		final SparkSession sparkSession = SparkSession.builder().appName(APP_NAME).master(MASTER).getOrCreate();

		final JavaRDD<String> stringJavaRDD = sparkSession.read().textFile(PATH).javaRDD();

		final JavaRDD<LabeledPoint> data = stringJavaRDD.map(line -> {
			final String[] split = line.split(",");
			final double label = Double.valueOf(split[split.length - 1]);
			final double[] doubleFeatures = new double[split.length-1];
			for(int i = 0; i < split.length-1; i++){
				doubleFeatures[i] = Double.valueOf(split[i]);
			}
			final Vector features  = Vectors.dense(doubleFeatures);
			return new LabeledPoint(label, features);
		});

		final Dataset<Row> dataFrame = sparkSession.createDataFrame(data, LabeledPoint.class);


		final Dataset<Row>[] splits = dataFrame.randomSplit(new double[]{0.6, 0.4}, 1234L);
		final Dataset<Row> train = splits[0];
		final Dataset<Row> test = splits[1];

		final int[] layers = new int[] {18,10, 2};

		final MultilayerPerceptronClassifier trainer = new MultilayerPerceptronClassifier()
				.setLayers(layers)
				.setBlockSize(128)
				.setSeed(1234L)
				.setMaxIter(500);

		final MultilayerPerceptronClassificationModel model = trainer.fit(train);

		final Dataset<Row> result = model.transform(test);

		final Dataset<Row> predictionAndLabels = result.select("prediction", "label");

		MulticlassClassificationEvaluator evaluator = new MulticlassClassificationEvaluator()
				.setMetricName("accuracy");

		System.out.println("Test set accuracy = " + evaluator.evaluate(predictionAndLabels));

	}

}
