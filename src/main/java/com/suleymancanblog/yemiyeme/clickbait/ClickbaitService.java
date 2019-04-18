package com.suleymancanblog.yemiyeme.clickbait;

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

	private static NewsLabel doubleResultConvertText(double result) {
		if (result == 1.0)
			return NewsLabel.ClICKBAIT;
		if (result == 0.0)
			return NewsLabel.NON_CLICKBAIT;
		throw new IllegalArgumentException("Unexpected prediction result: " + result);

	}

/*	public void test(){
		try {
			final model.data.corpus.ToArffFileCorpus toArffFileCorpus = new model.data.corpus.ToArffFileCorpus("/home/suleymancan/haber/test", util.Language.TR);
			final model.feature.corpus.CountFeatureCorpus countFeatures = engine.extracting.CountsExtractor.getCountFeatures(toArffFileCorpus);
			final util.FeatureMatrix featureMatrix = countFeatures.getFeatureMatrix();
			final List<FeatureMatrix> featureMatrices = Arrays.asList(featureMatrix);
			engine.writing.WriterEngine.createArffFiles(featureMatrices, "/home/suleymancan/haber/test");
		}
		catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		}
		catch (exception.T2AInputFileException e) {
			log.error(e.getMessage(), e);
		}
		catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		catch (exception.T2AInvalidParameterException e) {
			log.error(e.getMessage(), e);
		}
		catch (exception.T2AMatrixException e) {
			log.error(e.getMessage(), e);
		}
	}*/


	public NewsLabel predict(NewsFeaturesPrizma newsFeaturesPrizma) {
		//@formatter:off
		final JavaRDD<Vector> vectorJavaRDD = javaSparkContext.parallelize(Collections.singletonList(newsFeaturesPrizma))
				.map(newsFeature -> Vectors.dense( // order is important!
						newsFeature.getAvgParagraphLengthWithSpace(),
						newsFeature.getAvgParagraphLengthWithoutSpace(),
						newsFeature.getAvgSentenceLength(),
						newsFeature.getEmptyParagraphRatio(),
						newsFeature.getLengthOfTitle(),
						newsFeature.getParagraphCount(),
						newsFeature.getPunctuationAsteriksRatio(),
						newsFeature.getPunctuationColonRatio(),
						newsFeature.getPunctuationCommaRatio(),
						newsFeature.getPunctuationCountOfTitle(),
						newsFeature.getPunctuationDashRatio(),
						newsFeature.getPunctuationDoubleQuoteRatio(),
						newsFeature.getPunctuationEllipsisRatio(),
						newsFeature.getPunctuationExclamationRatio(),
						newsFeature.getPunctuationRatio(),
						newsFeature.getPunctuationSemicolonRatio(),
						newsFeature.getStopWordRatio(),
						newsFeature.getWordLengthVariance()
												 ));
		//@formatter:on
		final double predicition = decisionTreeModel.predict(vectorJavaRDD).first();
		return doubleResultConvertText(predicition);
	}
}
