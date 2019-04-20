package com.suleymancanblog.yemiyeme.prizma;

import com.google.common.primitives.Ints;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Codes in this class belong to the prizma project. (Minor improvements may have been made.)
 * https://code.google.com/archive/p/prizma-text-classification/
 *
 */
@Service
@AllArgsConstructor
public class NewsFeatureService {


	private final NewsFeauteUtilService newsFeauteUtilService;
	/**
	 *
	 * @author Ziynet Nesibe DAYIOGLU
	 * ziynetnesibe@gmail.com www.ziynetnesibe.com <br/>
	 *
	 * This method contains an attribute which counts the average <br/>
	 * length of the paragraphs inside one article without empty <br/>
	 * paragraphs
	 */
	public String avgParagraphLengthWithoutSpace(String source){
		Scanner scanner = new Scanner(source);
		int totalLength = 0;
		int paragraphCount = 0;

		while (scanner.hasNextLine()) {
			String paragraph = scanner.nextLine();
			if (paragraph.length() != 0) {
				totalLength += paragraph.length();
				paragraphCount++;
			}
		}
		scanner.close();

		int averageLength = Math.round((float) totalLength / paragraphCount);
		return Integer.toString(averageLength);
	}

	/**
	 *
	 * @author Ziynet Nesibe DAYIOGLU
	 * ziynetnesibe@gmail.com www.ziynetnesibe.com <br/>
	 *
	 * This method contains an attribute which counts the average length <br/>
	 * of the paragraphs inside one article with empty paragraphs included <br/>
	 */
	public String avgParagraphLengthWithSpace(String source) {
		Scanner scanner = new Scanner(source);
		int totalLength = 0;
		int paragraphCount = 0;
		while (scanner.hasNextLine()) {
			String paragraph = scanner.nextLine();
			totalLength += paragraph.length();
			paragraphCount++;
		}
		scanner.close();

		int avgParagraphLength = Math.round((float) totalLength / paragraphCount);
		return Integer.toString(avgParagraphLength);
	}

	/**
	 *
	 * @author hrzafer
	 */
	public String avgSentenceLength(String source) {

		int avgSentenceLength;
		int totalSentenceLength = 0;
		int sentenceCount = 0;
		Scanner s = new Scanner(source);
		StringBuilder sentence = new StringBuilder();
		while (s.hasNext()) {
			String token = s.next();
			sentence.append(token).append(' ');
			if (token.matches("[^(]*[^A-ZÇİĞÖŞÜ][\\.\\?!](\"|$)")) {
				sentenceCount++;
				totalSentenceLength += sentence.length() - 1;
				sentence.delete(0, sentence.length());
			}
		}
		s.close();

		avgSentenceLength = Math.round((float) totalSentenceLength / sentenceCount);
		return Integer.toString(avgSentenceLength);
	}

	/**
	 *
	 * @author Ziynet Nesibe DAYIOGLU
	 * ziynetnesibe@gmail.com www.ziynetnesibe.com <br/>
	 *
	 * This class contains an attribute which counts the empty paragraph ratio <br/>
	 * (empty paragraph count/total paragraph count)
	 */
	public String emptyParagraphRatio(String source) {
		Scanner scanner = new Scanner(source);
		int paragraphCount = 0;
		int emptyParagraphCount = 0;
		Pattern p = Pattern.compile("[a-zA-Z0-9ŞşÇçÖöĞğÜüİı]");

		while (scanner.hasNextLine()) {
			String paragraph = scanner.nextLine();
			Matcher m = p.matcher(paragraph);
			if (!m.find()) {
				emptyParagraphCount++;
			}
			paragraphCount++;
		}
		scanner.close();

		double ratio = (double) emptyParagraphCount / paragraphCount;
		return newsFeauteUtilService.formatDouble(ratio, "#.##");
	}

	/**
	 *
	 * @author Ziynet Nesibe DAYIOGLU
	 * ziynetnesibe@gmail.com www.ziynetnesibe.com <br/>
	 *
	 * This class contains an attribute which counts the length of the title
	 */
	public String lengthOfTitle(String source) {
		int letterCount = 0;
		String titleOfText = newsFeauteUtilService.getTitleOfText(source);
		if (titleOfText != null) {
			letterCount = titleOfText.length();
		}
		return Integer.toString(letterCount);
	}



	/**
	 *
	 * @author hrzafer
	 */
	public String paragraphCount(String source) {

		Scanner s = new Scanner(source);
		Integer paragraphCount = 0;
		while (s.hasNextLine()) {
			paragraphCount++;
			s.nextLine();
		}

		s.close();

		return paragraphCount.toString();
	}

	/**
	 *
	 * @author hrzafer
	 */
	public String stopWordRatio(String source) {
		source = source.replaceAll("[\\*\\-\\:\\\"\\.,\\(\\);?!']", "");
		Scanner s = new Scanner(source);
		int stopwordCount = 0;
		int wordCount = 0;
		while (s.hasNext()) {
			String token = s.next();
			for (String stopword : newsFeauteUtilService.stopWords) {
				if (token.equalsIgnoreCase(stopword)) {
					stopwordCount++;
				}
			}
			wordCount++;
		}
		s.close();
		double ratio = (double) stopwordCount / wordCount;
		return newsFeauteUtilService.formatDouble(ratio, "#.##");
	}


	/**
	 *
	 * @author Ziynet Nesibe DAYIOGLU
	 * ziynetnesibe@gmail.com www.ziynetnesibe.com <br/>
	 *
	 * This class contains an attribute which counts the subtitle of the given article
	 */
	public String subtitleRatio(String source) {
		Scanner scanner = new Scanner(source);
		int subtitleCount = 0;
		int paragraphCount = 0;
		if (scanner.hasNextLine()) {
			scanner.nextLine();
		}
		while (scanner.hasNextLine()) {
			String paragraph = scanner.nextLine();
			paragraphCount++;
			Pattern p = Pattern.compile("[a-zA-Z0-9ŞşÇçÖöĞğÜüİı]");
			Pattern p2 = Pattern.compile("[^a-zA-Z0-9 ŞşÇçÖöĞğÜüİı',]");
			Matcher m = p.matcher(paragraph);
			Matcher m2 = p2.matcher(paragraph);
			if (m.find() && !m2.find() && paragraph.length() < 60) {
				subtitleCount++;
			}
		}
		scanner.close();
		double ratio = (double) subtitleCount / paragraphCount;
		return newsFeauteUtilService.formatDouble(ratio, "#.###");
	}

	/**
	 *
	 * @author hrzafer
	 */
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

		if (wordCount < 1){
			return Double.toString(Double.NaN);
		}

		double mean = (double) totalWordLen / wordCount;
		int[] values = Ints.toArray(lenghts);
		int variance = (int) Math.round(MAT.variance(values, mean));

		return Integer.toString(variance);
	}


}
