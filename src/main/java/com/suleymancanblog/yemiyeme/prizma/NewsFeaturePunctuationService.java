package com.suleymancanblog.yemiyeme.prizma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Codes in this class belong to the prizma project. (Minor improvements may have been made.)
 * https://code.google.com/archive/p/prizma-text-classification/
 *
 */
@Service
public class NewsFeaturePunctuationService {

	@Autowired
	private NewsFeauteUtilService newsFeauteUtilService;

	/**
	 *
	 * @author test
	 */
	private static final char ASTERIKS = '*';
	private static final char COLON = ':';
	private static final char SEMI_COLON = ';';
	private static final char COMMA = ',';
	private static final char DASH = '-';
	private static final char DOUBLE_QUOTE = '"';
	private static final char EXCLAMATION = '!';
	private static final char QUESTION_MARK = '?';

	private NewsFeaturePunctuationDataExtractor extractor;


	/**
	 *
	 * @author test
	 */
	 private String extractRatioAsFormattedString(String source, char ch) {
		extractor = new NewsFeaturePunctuationDataExtractor(source, ch);
		double ratio = getPunctuationRatio();
		return newsFeauteUtilService.formatDouble(ratio, "#.###");
	}

	/**
	 *
	 * @author test
	 */
	private double getPunctuationRatio() {
		int punctuationCount = extractor.getPunctuationCount();
		int totalPunctuationCount = extractor.getTotalPunctuationCount();
		double ratio = ((double) punctuationCount / totalPunctuationCount);
		return ratio;
	}


	/**
	 *
	 * @author hrzafer
	 */
	public String punctuationAsteriksRatio(String source){
		return extractRatioAsFormattedString(source, ASTERIKS);
	}

	/**
	 *
	 * @author hrzafer
	 */
	public String punctuationColonRatio(String source){
		return extractRatioAsFormattedString(source, COLON);
	}
	/**
	 *
	 * @author hrzafer
	 */
	public String punctuationCommaRatio(String source){
		return extractRatioAsFormattedString(source, COMMA);
	}

	/**
	 *
	 * @author Ziynet Nesibe DAYIOGLU
	 * ziynetnesibe@gmail.com www.ziynetnesibe.com <br/>
	 *
	 * This class contains an attribute which counts the punctuation mark count of the title
	 */
	public String punctuationCountOfTitle(String source) {
		int punctCount = 0;
		String titleOfText = newsFeauteUtilService.getTitleOfText(source);
		if (titleOfText != null) {
			Pattern p = Pattern.compile("[^a-zA-Z0-9 ŞşÇçÖöĞğÜüİı]");
			Matcher m = p.matcher(titleOfText);

			for (int c = 0; c < titleOfText.length(); c++) {
				if (m.find()) {
					punctCount++;
				}
			}
		}
		return Integer.toString(punctCount);
	}

	/**
	 *
	 * @author hrzafer
	 */
	public String punctuationDashRatio(String source){
		return extractRatioAsFormattedString(source, DASH);
	}

	/**
	 *
	 * @author hrzafer
	 */
	public String punctuationDoubleQuoteRatio(String source){
		return extractRatioAsFormattedString(source, DOUBLE_QUOTE);
	}

	/**
	 *
	 * @author hrzafer
	 */
	public String punctuationEllipsisRatio(String source) {
		NewsFeaturePunctuationDataExtractor extractor = new NewsFeaturePunctuationDataExtractor(source, '.');
		int totalPunctuationCount = extractor.getTotalPunctuationCount();
		int ellipsisCount = 0;
		Pattern pattern = Pattern.compile("\\.\\.\\.");
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			ellipsisCount++;
		}
		totalPunctuationCount -= ellipsisCount * 2;
		double ratio = (double) ellipsisCount / totalPunctuationCount;
		return newsFeauteUtilService.formatDouble(ratio, "#.###");
	}

	/**
	 *
	 * @author hrzafer
	 */
	public String punctuationExclamationRatio(String source){
		return extractRatioAsFormattedString(source, EXCLAMATION);
	}

	/**
	 *
	 * @author hrzafer
	 */
	public String punctuationRatio(String source) {
		NewsFeaturePunctuationDataExtractor extractor = new NewsFeaturePunctuationDataExtractor(source, Character.MAX_VALUE);
		int totalPunctuationCount = extractor.getTotalPunctuationCount();
		int letterOrDigitCount = extractor.getLetterOrDigitCount();
		double ratio = (double) totalPunctuationCount / letterOrDigitCount;
		return newsFeauteUtilService.formatDouble(ratio, "#.##");
	}

	public String punctuationSemiColonRatio(String source){
		return extractRatioAsFormattedString(source, SEMI_COLON);
	}







}
