package com.suleymancanblog.yemiyeme.clickbait;

import lombok.AllArgsConstructor;
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
public class ClickbaitService {

	private final JavaSparkContext javaSparkContext;

	private final DecisionTreeModel decisionTreeModel;

	private static NewsLabel doubleResultConvertText(double result) {
		if (result == 1.0)
			return NewsLabel.ClICKBAIT;
		if (result == 0.0)
			return NewsLabel.NON_CLICKBAIT;
		throw new IllegalArgumentException("Unexpected prediction result: " + result);

	}

	public NewsLabel predict(NewsFeatures newsFeatures) {
		//@formatter:off
		final JavaRDD<Vector> vectorJavaRDD = javaSparkContext.parallelize(Collections.singletonList(newsFeatures))
				.map(newsFeature -> Vectors.dense( // order is important!
						newsFeature.getCountSentence(),
						newsFeature.getCountWord(),
						newsFeature.getCountLetter(),
						newsFeature.getCountInvertedSentence(),
						newsFeature.getCountAdditional(),
						newsFeature.getCountTripleDot(),
						newsFeature.getCountDot(),
						newsFeature.getCountQuestionMark(),
						newsFeature.getCountExclamationPoint(),
						newsFeature.getCountSpace(),
						newsFeature.getCountComma(),
						newsFeature.getCountSemiColon(),
						newsFeature.getCountColon(),
						newsFeature.getCountQuote(),
						newsFeature.getCountWordPerSentence(),
						newsFeature.getCountAdditionalPerSentence(),
						newsFeature.getCountAdditionalPerWord(),
						newsFeature.getCountLetterPerWord()
												 ));
		//@formatter:on
		final double predicition = decisionTreeModel.predict(vectorJavaRDD).first();
		return doubleResultConvertText(predicition);
	}
}
