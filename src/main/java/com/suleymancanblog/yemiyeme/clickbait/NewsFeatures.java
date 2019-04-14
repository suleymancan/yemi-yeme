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
public class NewsFeatures implements Serializable {

	private double countSentence;

	private double countWord;

	private double countLetter;

	private double countInvertedSentence;

	private double countAdditional;

	private double countTripleDot;

	private double countDot;

	private double countQuestionMark;

	private double countExclamationPoint;

	private double countSpace;

	private double countComma;

	private double countSemiColon;

	private double countColon;

	private double countQuote;

	private double countWordPerSentence;

	private double countAdditionalPerSentence;

	private double countAdditionalPerWord;

	private double countLetterPerWord;


}
