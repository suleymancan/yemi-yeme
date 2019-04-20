package com.suleymancanblog.yemiyeme.prizma;

import com.google.common.primitives.Ints;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Codes in this class belong to the prizma project.
 * https://code.google.com/archive/p/prizma-text-classification/
 */
@Service
@AllArgsConstructor
public class PrizmaFeatureService {

	private final PrizmaFeatureUtilService prizmaFeatureUtilService;

	public String lengthOfTitle(String source) {
		int letterCount = 0;
		String titleOfText = prizmaFeatureUtilService.getTitleOfText(source);
		if (titleOfText != null) {
			letterCount = titleOfText.length();
		}
		return Integer.toString(letterCount);
	}

	public String numberCount(String source) {
		source = source.replaceAll(PrizmaStringHelper.CLEAN_REGEX, " ");
		Scanner s = new Scanner(source);
		Integer numberCount = 0;
		while (s.hasNext()) {
			if (s.hasNextInt()) {
				numberCount++;
			}
			s.next();
		}

		return numberCount.toString();
	}

	public String stopWordRatio(String source) {
		source = source.replaceAll("[\\*\\-\\:\\\"\\.,\\(\\);?!']", "");
		Scanner s = new Scanner(source);
		int stopwordCount = 0;
		int wordCount = 0;
		while (s.hasNext()) {
			String token = s.next();
			for (String stopword : prizmaFeatureUtilService.stopWords) {
				if (token.equalsIgnoreCase(stopword)) {
					stopwordCount++;
				}
			}
			wordCount++;
		}
		s.close();
		double ratio = (double) stopwordCount / wordCount;
		return prizmaFeatureUtilService.formatDouble(ratio, "#.##");
	}

	public String whyCount(String source) {
		Scanner s;
		Integer whyCount = 0;
		s = new Scanner(source);

		while (s.hasNext()) {
			String token = s.next();
			if (token.matches("(neden|niçin|niye)($|!|\\?|\\.)")) {
				whyCount++;
			}
		}

		return whyCount.toString();
	}

	public String wordCountOfTitle(String source) {

		Integer wordCount = 0;
		String titleOfText = prizmaFeatureUtilService.getTitleOfText(source);
		if (titleOfText != null) {
			StringTokenizer st = new StringTokenizer(titleOfText, " ,';?.");
			while (st.hasMoreTokens()) {
				st.nextToken();
				wordCount++;
			}
		}
		return wordCount.toString();
	}

	public String wordLengthAverage(String source) {
		source = source.replaceAll("[\\*-\\:\\\"\\.,'\\(\\);?!]", "");
		Scanner s = new Scanner(source);
		int wordCount = 0;
		int totalWordLen = 0;
		while (s.hasNext()) {
			String token = s.next();
			totalWordLen += token.length();
			wordCount++;
		}
		s.close();
		double mean = (double) totalWordLen / wordCount;
		return String.format(Locale.US, "%.2f", mean);
	}

	public String wordLengthVariance(String source) {
		source = source.replaceAll("[\\*-\\:\\\"\\.,'\\(\\);?!]", "");
		Scanner s = new Scanner(source);
		int wordCount = 0;
		int totalWordLen = 0;
		List<Integer> lenghts = new ArrayList();

		while (s.hasNext()) {
			String token = s.next();
			int tokenLength = token.length();
			totalWordLen += tokenLength;
			wordCount++;
			lenghts.add(new Integer(tokenLength));
		}
		s.close();

		if (wordCount < 1) {
			return Double.toString(Double.NaN);
		}

		double mean = (double) totalWordLen / wordCount;
		int[] values = Ints.toArray(lenghts);
		int variance = (int) Math.round(PrizmaMAT.variance(values, mean));

		return Integer.toString(variance);
	}

}
