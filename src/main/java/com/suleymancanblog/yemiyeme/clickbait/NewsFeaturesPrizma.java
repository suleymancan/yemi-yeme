package com.suleymancanblog.yemiyeme.clickbait;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on April, 2019
 *
 * @author suleymancan
 */
@Getter
@Setter
public class NewsFeaturesPrizma implements Serializable {

	private double avgParagraphLengthWithoutSpace;

	private double avgParagraphLengthWithSpace;

	private double avgSentenceLength;

	private double emptyParagraphRatio;

	private double lengthOfTitle;

	private double paragraphCount;

	private double punctuationAsteriksRatio;

	private double punctuationColonRatio;

	private double punctuationCommaRatio;

	private double punctuationCountOfTitle;

	private double punctuationDashRatio;

	private double punctuationDoubleQuoteRatio;

	private double punctuationEllipsisRatio;

	private double punctuationExclamationRatio;

	private double punctuationRatio;

	private double punctuationSemicolonRatio;

	private double stopWordRatio;

	private double subtitleRatio;

	private double wordLengthVariance;


}
