package com.suleymancanblog.yemiyeme;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * Created on May, 2019
 *
 * @author suleymancan
 */

public class YemiYemeMultiLayerPerceptron {

	private static final String DATA_PATH = "/home/suleymancan/dev/yemi-yeme/src/main/resources/static/yemi-yeme-prizma-dataset.arff";

	public static void main(String[] args) {
		try {
			final ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(DATA_PATH);
			final Instances instances = dataSource.getDataSet();
			instances.setClassIndex(instances.numAttributes()-1);

			final MultilayerPerceptron multilayerPerceptron = new MultilayerPerceptron();

			multilayerPerceptron.buildClassifier(instances);

			final Evaluation evaluation = new Evaluation(instances);
			evaluation.evaluateModel(multilayerPerceptron, instances);
			System.out.println(evaluation.toSummaryString()); //Summary of Training

			weka.core.SerializationHelper.write("weka-mlp-yemi-yeme.model", multilayerPerceptron);

//			TEST
//			final MultilayerPerceptron readMLP = (MultilayerPerceptron) SerializationHelper.read("weka-mlp-yemi-yeme.model");
//
//			final ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(DATA_PATH);
//			final Instances instances = dataSource.getDataSet();
//
//			instances.setClassIndex(instances.numAttributes()-1);
//
//			final double actualValue = instances.instance(0).classValue();
//
//			final Instance instance = instances.instance(0);
//
//			double predMLP = readMLP.classifyInstance(instance);
//
//			System.out.println(actualValue + " " + predMLP);

		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
