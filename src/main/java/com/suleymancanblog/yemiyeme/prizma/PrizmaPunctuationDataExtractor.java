package com.suleymancanblog.yemiyeme.prizma;

import lombok.NoArgsConstructor;

/**
 * Codes in this class belong to the prizma project.
 * https://code.google.com/archive/p/prizma-text-classification/
 */
@NoArgsConstructor
public class PrizmaPunctuationDataExtractor {

	private int punctuationCount;

	private int totalPunctuationCount;

	private int letterOrDigitCount;

	PrizmaPunctuationDataExtractor(String source, char punctuation) {
		extractPunctuationData(source, punctuation);
	}

	private void extractPunctuationData(String source, char punctuation) {
		char[] data = source.toCharArray();
		for (char c : data) {
			if (c == punctuation) {
				punctuationCount++;
			}
			// Burada else if değil özellikle if kullanıyoruz ki
			// aradığımız punctuation toplam punctuation sayısına da dahil edilsin.
			if (Character.isLetterOrDigit(c)) {
				letterOrDigitCount++;
			}
			else if (!Character.isWhitespace(c)) {
				totalPunctuationCount++;
			}
		}
	}

	int getLetterOrDigitCount() {
		return letterOrDigitCount;
	}

	int getPunctuationCount() {
		return punctuationCount;
	}

	int getTotalPunctuationCount() {
		return totalPunctuationCount;
	}
}

