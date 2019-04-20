package com.suleymancanblog.yemiyeme.clickbait;

import lombok.*;

import java.io.Serializable;

/**
 * Created on April, 2019
 *
 * @author suleymancan
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrizmaFeature implements Serializable {

	private double lengthOfTitle;

	private double numberCount;

	private double punctuationColonRatio;

	private double punctuationCommaRatio;

	private double punctuationCountOfTitle;

	private double punctuationDashRatio;

	private double punctuationDoubleQuoteRatio;

	private double punctuationEllipsisRatio;

	private double punctuationExclamationRatio;

	private double punctuationQuestionMarkRatio;

	private double punctuationRatio;

	private double punctuationSemiColonRatio;

	private double stopWordRatio;

	private double whyCount;

	private double wordCountOfTitle;

	private double wordLengthAverage;

	private double wordLengthVariance;

}
