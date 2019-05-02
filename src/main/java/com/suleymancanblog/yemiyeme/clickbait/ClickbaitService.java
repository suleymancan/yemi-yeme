package com.suleymancanblog.yemiyeme.clickbait;

import com.suleymancanblog.yemiyeme.prizma.PrizmaPunctuationService;
import com.suleymancanblog.yemiyeme.prizma.PrizmaFeatureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.springframework.stereotype.Service;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.DenseInstance;
import weka.core.Instance;

import java.util.Collections;

/**
 * Created on April, 2019
 *
 * @author suleymancan
 */
@Service
@AllArgsConstructor
@Slf4j
public class ClickbaitService {

	private final JavaSparkContext javaSparkContext;

	private final DecisionTreeModel decisionTreeModel;

	private final MultilayerPerceptron multilayerPerceptron;

	private final PrizmaFeatureService prizmaFeatureService;

	private final PrizmaPunctuationService newsFeaturePunctuationService;

	private NewsLabel doubleResultConvertText(double result) {
		if (result == 1.0)
			return NewsLabel.CLICKBAIT;
		if (result == 0.0)
			return NewsLabel.NOT_CLICKBAIT;
		throw new IllegalArgumentException("Unexpected prediction result: " + result);

	}

	PrizmaFeature createNewsFeature(String source){
		//@formatter:off
		final PrizmaFeature prizmaFeature = PrizmaFeature.builder()
				.lengthOfTitle(Double.valueOf(prizmaFeatureService.lengthOfTitle(source)))
				.numberCount(Double.valueOf(prizmaFeatureService.numberCount(source)))
				.punctuationColonRatio(Double.valueOf(newsFeaturePunctuationService.punctuationColonRatio(source)))
				.punctuationCommaRatio(Double.valueOf(newsFeaturePunctuationService.punctuationCommaRatio(source)))
				.punctuationCountOfTitle(Double.valueOf(newsFeaturePunctuationService.punctuationCountOfTitle(source)))
				.punctuationDashRatio(Double.valueOf(newsFeaturePunctuationService.punctuationDashRatio(source)))
				.punctuationDoubleQuoteRatio(Double.valueOf(newsFeaturePunctuationService.punctuationDoubleQuoteRatio(source)))
				.punctuationEllipsisRatio(Double.valueOf(newsFeaturePunctuationService.punctuationEllipsisRatio(source)))
				.punctuationExclamationRatio(Double.valueOf(newsFeaturePunctuationService.punctuationExclamationRatio(source)))
				.punctuationQuestionMarkRatio(Double.valueOf(newsFeaturePunctuationService.punctuationQuestionMarkRatio(source)))
				.punctuationRatio(Double.valueOf(newsFeaturePunctuationService.punctuationRatio(source)))
				.punctuationSemiColonRatio(Double.valueOf(newsFeaturePunctuationService.punctuationSemiColonRatio(source)))
				.stopWordRatio(Double.valueOf(prizmaFeatureService.stopWordRatio(source)))
				.whyCount(Double.valueOf(prizmaFeatureService.whyCount(source)))
				.wordCountOfTitle(Double.valueOf(prizmaFeatureService.wordCountOfTitle(source)))
				.wordLengthAverage(Double.valueOf(prizmaFeatureService.wordLengthAverage(source)))
				.wordLengthVariance(Double.valueOf(prizmaFeatureService.wordLengthVariance(source)))
				.build();
		//@formatter:on
		return prizmaFeature;
	}


	NewsLabel predict(PrizmaFeature prizmaFeature) {
		//@formatter:off
		final JavaRDD<Vector> vectorJavaRDD = javaSparkContext.parallelize(Collections.singletonList(prizmaFeature))
				.map(feature -> Vectors.dense( // order is important!
						feature.getLengthOfTitle(),
						feature.getNumberCount(),
						feature.getPunctuationColonRatio(),
						feature.getPunctuationCommaRatio(),
						feature.getPunctuationCountOfTitle(),
						feature.getPunctuationDashRatio(),
						feature.getPunctuationDoubleQuoteRatio(),
						feature.getPunctuationEllipsisRatio(),
						feature.getPunctuationExclamationRatio(),
						feature.getPunctuationQuestionMarkRatio(),
						feature.getPunctuationRatio(),
						feature.getPunctuationSemiColonRatio(),
						feature.getStopWordRatio(),
						feature.getWhyCount(),
						feature.getWordCountOfTitle(),
						feature.getWordLengthAverage(),
						feature.getWordLengthVariance()
												 ));
		//@formatter:on
		final double predicition = decisionTreeModel.predict(vectorJavaRDD).first();
		return doubleResultConvertText(predicition);
	}


	NewsLabel predictWeka(PrizmaFeature prizmaFeature) throws Exception {
		//@formatter:off
		final Instance instance = new DenseInstance(18);
		instance.setValue(0, prizmaFeature.getLengthOfTitle());
		instance.setValue(1, prizmaFeature.getNumberCount());
		instance.setValue(2, prizmaFeature.getPunctuationColonRatio());
		instance.setValue(3, prizmaFeature.getPunctuationCommaRatio());
		instance.setValue(4, prizmaFeature.getPunctuationCountOfTitle());
		instance.setValue(5, prizmaFeature.getPunctuationDashRatio());
		instance.setValue(6, prizmaFeature.getPunctuationDoubleQuoteRatio());
		instance.setValue(7, prizmaFeature.getPunctuationEllipsisRatio());
		instance.setValue(8, prizmaFeature.getPunctuationExclamationRatio());
		instance.setValue(9, prizmaFeature.getPunctuationQuestionMarkRatio());
		instance.setValue(10, prizmaFeature.getPunctuationRatio());
		instance.setValue(11, prizmaFeature.getPunctuationSemiColonRatio());
		instance.setValue(12, prizmaFeature.getStopWordRatio());
		instance.setValue(13, prizmaFeature.getWhyCount());
		instance.setValue(14, prizmaFeature.getWordCountOfTitle());
		instance.setValue(15, prizmaFeature.getWordLengthAverage());
		instance.setValue(16, prizmaFeature.getWordLengthVariance());

		// index 17: class

		//@formatter:on
		final double prediction = multilayerPerceptron.classifyInstance(instance);
		return doubleResultConvertText(prediction);
	}



}
