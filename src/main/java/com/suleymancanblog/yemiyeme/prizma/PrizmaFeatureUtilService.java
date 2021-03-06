package com.suleymancanblog.yemiyeme.prizma;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

/**
 * Codes in this class belong to the prizma project.
 * https://code.google.com/archive/p/prizma-text-classification/
 */
@Service
public class PrizmaFeatureUtilService {

	static final String[] stopWords = { "acaba", "altı", "ama", "ancak", "artık", "asla", "aslında", "az", "bana", "bazen", "bazı", "bazıları",
			"bazısı", "belki", "ben", "beni", "benim", "beş", "bile", "bir", "birçoğu", "birçok", "birçokları", "biri", "birisi", "birkaç", "birkaçı",
			"birşey", "birşeyi", "biz", "bize", "bizi", "bizim", "böyle", "böylece", "bu", "buna", "bunda", "bundan", "bunu", "bunun", "burada",
			"bütün", "çoğu", "çoğuna", "çoğunu", "çok", "çünkü", "da", "daha", "de", "değil", "demek", "diğer", "diğeri", "diğerleri", "diye",
			"dokuz", "dolayı", "dört", "elbette", "en", "fakat", "falan", "felan", "filan", "gene", "gibi", "hâlâ", "hangi", "hangisi", "hani",
			"hatta", "hem", "henüz", "hep", "hepsi", "hepsine", "hepsini", "her", "her biri", "herkes", "herkese", "herkesi", "hiç", "hiç kimse",
			"hiçbiri", "hiçbirine", "hiçbirini", "için", "içinde", "iki", "ile", "ise", "işte", "kaç", "kadar", "kendi", "kendine", "kendini", "ki",
			"kim", "kime", "kimi", "kimin", "kimisi", "madem", "mı", "mı", "mi", "mu", "mu", "mü", "mü", "nasıl", "ne", "ne kadar", "ne zaman",
			"neden", "nedir", "nerde", "nerede", "nereden", "nereye", "nesi", "neyse", "niçin", "niye", "on", "ona", "ondan", "onlar", "onlara",
			"onlardan", "onların", "onların", "onu", "onun", "orada", "oysa", "oysaki", "öbürü", "ön", "önce", "ötürü", "öyle", "rağmen", "sana",
			"sekiz", "sen", "senden", "seni", "senin", "siz", "sizden", "size", "sizi", "sizin", "son", "sonra", "şayet", "şey", "şeyden", "şeye",
			"şeyi", "şeyler", "şimdi", "şöyle", "şu", "şuna", "şunda", "şundan", "şunlar", "şunu", "şunun", "tabi", "tamam", "tüm", "tümü", "üç",
			"üzere", "var", "ve", "veya", "veyahut", "ya", "yada", "yani", "yedi", "yerine", "yine", "yoksa", "zaten", "zira" };

	String formatDouble(double value, String pattern) {
		if (Double.isNaN(value) || Double.isInfinite(value)) {
			return Double.toString(Double.NaN);
		}
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
		DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern(pattern);
		return df.format(value);
	}

	String getTitleOfText(String source) {
		Scanner scanner = new Scanner(source);
		if (scanner.hasNextLine()) {
			return scanner.nextLine();
		}
		scanner.close();
		return null;
	}

}
