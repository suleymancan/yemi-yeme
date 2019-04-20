package com.suleymancanblog.yemiyeme.prizma;

import lombok.*;

import java.io.Serializable;

/**
 * Codes in this class belong to the prizma project. (Minor improvements may have been made.)
 * https://code.google.com/archive/p/prizma-text-classification/
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsFeature implements Serializable {

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

	private double punctuationSemiColonRatio;

	private double stopWordRatio;

	private double subtitleRatio;

	private double wordLengthVariance;




}
