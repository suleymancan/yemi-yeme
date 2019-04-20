package com.suleymancanblog.yemiyeme.prizma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Codes in this class belong to the prizma project.
 * https://code.google.com/archive/p/prizma-text-classification/
 */
@Service
public class PrizmaPunctuationService {

	private static final char COLON = ':';

	private static final char SEMI_COLON = ';';

	private static final char COMMA = ',';

	private static final char DASH = '-';

	private static final char DOUBLE_QUOTE = '"';

	private static final char EXCLAMATION = '!';

	private static final char QUESTION_MARK = '?';

	@Autowired
	private PrizmaFeatureUtilService prizmaFeatureUtilService;

	private PrizmaPunctuationDataExtractor extractor;

	private String extractRatioAsFormattedString(String source, char ch) {
		extractor = new PrizmaPunctuationDataExtractor(source, ch);
		double ratio = getPunctuationRatio();
		return prizmaFeatureUtilService.formatDouble(ratio, "#.###");
	}

	private double getPunctuationRatio() {
		int punctuationCount = extractor.getPunctuationCount();
		int totalPunctuationCount = extractor.getTotalPunctuationCount();
		double ratio = ((double) punctuationCount / totalPunctuationCount);
		return ratio;
	}

	public String punctuationColonRatio(String source) {
		return extractRatioAsFormattedString(source, COLON);
	}

	public String punctuationCommaRatio(String source) {
		return extractRatioAsFormattedString(source, COMMA);
	}

	public String punctuationCountOfTitle(String source) {
		int punctCount = 0;
		String titleOfText = prizmaFeatureUtilService.getTitleOfText(source);
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

	public String punctuationDashRatio(String source) {
		return extractRatioAsFormattedString(source, DASH);
	}

	public String punctuationDoubleQuoteRatio(String source) {
		return extractRatioAsFormattedString(source, DOUBLE_QUOTE);
	}

	public String punctuationEllipsisRatio(String source) {
		PrizmaPunctuationDataExtractor extractor = new PrizmaPunctuationDataExtractor(source, '.');
		int totalPunctuationCount = extractor.getTotalPunctuationCount();
		int ellipsisCount = 0;
		Pattern pattern = Pattern.compile("\\.\\.\\.");
		Matcher matcher = pattern.matcher(source);
		while (matcher.find()) {
			ellipsisCount++;
		}
		totalPunctuationCount -= ellipsisCount * 2;
		double ratio = (double) ellipsisCount / totalPunctuationCount;
		return prizmaFeatureUtilService.formatDouble(ratio, "#.###");
	}

	public String punctuationExclamationRatio(String source) {
		return extractRatioAsFormattedString(source, EXCLAMATION);
	}

	public String punctuationQuestionMarkRatio(String source) {
		return extractRatioAsFormattedString(source, QUESTION_MARK);
	}

	public String punctuationRatio(String source) {
		PrizmaPunctuationDataExtractor extractor = new PrizmaPunctuationDataExtractor(source, Character.MAX_VALUE);
		int totalPunctuationCount = extractor.getTotalPunctuationCount();
		int letterOrDigitCount = extractor.getLetterOrDigitCount();
		double ratio = (double) totalPunctuationCount / letterOrDigitCount;
		return prizmaFeatureUtilService.formatDouble(ratio, "#.##");
	}

	public String punctuationSemiColonRatio(String source) {
		return extractRatioAsFormattedString(source, SEMI_COLON);
	}

}
