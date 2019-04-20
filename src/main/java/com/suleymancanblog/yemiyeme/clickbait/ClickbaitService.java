package com.suleymancanblog.yemiyeme.clickbait;

import com.suleymancanblog.yemiyeme.prizma.NewsFeature;
import com.suleymancanblog.yemiyeme.prizma.NewsFeaturePunctuationService;
import com.suleymancanblog.yemiyeme.prizma.NewsFeatureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import org.springframework.stereotype.Service;

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

	private final NewsFeatureService newsFeatureService;

	private final NewsFeaturePunctuationService newsFeaturePunctuationService;

	private static NewsLabel doubleResultConvertText(double result) {
		if (result == 1.0)
			return NewsLabel.ClICKBAIT;
		if (result == 0.0)
			return NewsLabel.NON_CLICKBAIT;
		throw new IllegalArgumentException("Unexpected prediction result: " + result);

	}

	public NewsFeature createNewsFeature(String source){
		//@formatter:off
		final NewsFeature newsFeature = NewsFeature.builder()
				.avgParagraphLengthWithoutSpace(Double.valueOf(newsFeatureService.avgParagraphLengthWithoutSpace(source)))
				.avgParagraphLengthWithSpace(Double.valueOf(newsFeatureService.avgParagraphLengthWithSpace(source)))
				.avgSentenceLength(Double.valueOf(newsFeatureService.avgSentenceLength(source)))
				.emptyParagraphRatio(Double.valueOf(newsFeatureService.emptyParagraphRatio(source)))
				.lengthOfTitle(Double.valueOf(newsFeatureService.lengthOfTitle(source)))
				.paragraphCount(Double.valueOf(newsFeatureService.paragraphCount(source)))
				.punctuationAsteriksRatio(Double.valueOf(newsFeaturePunctuationService.punctuationAsteriksRatio(source)))
				.punctuationColonRatio(Double.valueOf(newsFeaturePunctuationService.punctuationColonRatio(source)))
				.punctuationCommaRatio(Double.valueOf(newsFeaturePunctuationService.punctuationCommaRatio(source)))
				.punctuationCountOfTitle(Double.valueOf(newsFeaturePunctuationService.punctuationCountOfTitle(source)))
				.punctuationDashRatio(Double.valueOf(newsFeaturePunctuationService.punctuationDashRatio(source)))
				.punctuationDoubleQuoteRatio(Double.valueOf(newsFeaturePunctuationService.punctuationDoubleQuoteRatio(source)))
				.punctuationEllipsisRatio(Double.valueOf(newsFeaturePunctuationService.punctuationEllipsisRatio(source)))
				.punctuationExclamationRatio(Double.valueOf(newsFeaturePunctuationService.punctuationExclamationRatio(source)))
				.punctuationRatio(Double.valueOf(newsFeaturePunctuationService.punctuationRatio(source)))
				.punctuationSemiColonRatio(Double.valueOf(newsFeaturePunctuationService.punctuationSemiColonRatio(source)))
				.stopWordRatio(Double.valueOf(newsFeatureService.stopWordRatio(source)))
				.subtitleRatio(Double.valueOf(newsFeatureService.subtitleRatio(source)))
				.wordLengthVariance(Double.valueOf(newsFeatureService.wordLengthVariance(source)))
				.build();
		//@formatter:on
		return newsFeature;
	}


	public NewsLabel predict(NewsFeature newsFeature) {
		//@formatter:off
		final JavaRDD<Vector> vectorJavaRDD = javaSparkContext.parallelize(Collections.singletonList(newsFeature))
				.map(feature -> Vectors.dense( // order is important!
						feature.getAvgParagraphLengthWithSpace(),
						feature.getAvgParagraphLengthWithoutSpace(),
						feature.getAvgSentenceLength(),
						feature.getEmptyParagraphRatio(),
						feature.getLengthOfTitle(),
						feature.getParagraphCount(),
						feature.getPunctuationAsteriksRatio(),
						feature.getPunctuationColonRatio(),
						feature.getPunctuationCommaRatio(),
						feature.getPunctuationCountOfTitle(),
						feature.getPunctuationDashRatio(),
						feature.getPunctuationDoubleQuoteRatio(),
						feature.getPunctuationEllipsisRatio(),
						feature.getPunctuationExclamationRatio(),
						feature.getPunctuationRatio(),
						feature.getPunctuationSemiColonRatio(),
						feature.getStopWordRatio(),
						feature.getWordLengthVariance()
												 ));
		//@formatter:on
		final double predicition = decisionTreeModel.predict(vectorJavaRDD).first();
		return doubleResultConvertText(predicition);
	}
}
