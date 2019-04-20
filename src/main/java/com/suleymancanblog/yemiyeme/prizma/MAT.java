package com.suleymancanblog.yemiyeme.prizma;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Codes in this class belong to the prizma project. ((Minor improvements may have been made.)
 * https://code.google.com/archive/p/prizma-text-classification/
 *
 */
public class MAT {
	public MAT() {
	}

	public static BigInteger factorial(int n) {
		BigInteger fact = BigInteger.ONE;

		for(int i = n; i > 1; --i) {
			fact = fact.multiply(new BigInteger(Integer.toString(i)));
		}

		return fact;
	}

	public static List<Integer> primes(int n) {
		List<Integer> primes = new ArrayList();
		if (n < 2) {
			throw new IllegalArgumentException("n must be an integer that n > 1");
		} else {
			int count;
			for(count = 2; count <= n; ++count) {
				primes.add(count);
			}

			count = 0;

			for(int i = 0; i * i < n; ++i) {
				int prime = (Integer)primes.get(i);

				for(int j = primes.size() - 1; j > i + 1; --j) {
					if (j >= i * i) {
						++count;
						if ((Integer)primes.get(j) % prime == 0) {
							primes.remove(j);
						}
					}
				}
			}

			return primes;
		}
	}

	public int gcd(int m, int n) {
		while(n != 0) {
			int r = m % n;
			m = n;
			n = r;
		}

		return m;
	}

	public static double mean(int[] values) {
		if (values.length == 0) {
			return 0.0D / 0.0;
		} else {
			int total = 0;
			int[] arr$ = values;
			int len$ = values.length;

			for(int i$ = 0; i$ < len$; ++i$) {
				int value = arr$[i$];
				total += value;
			}

			return (double)total / (double)values.length;
		}
	}

	public static double variance(int[] values, double mean) {
		double variance = 0.0D / 0.0;
		if (values.length == 1) {
			variance = 0.0D;
		} else {
			if (values.length <= 1) {
				throw new IllegalArgumentException("Length of values[] must be greater than 0");
			}

			double accumulatorFirst = 0.0D;
			double accumulatorSecond = 0.0D;

			for(int i = 0; i < values.length; ++i) {
				accumulatorFirst += Math.pow((double)values[i] - mean, 2.0D);
				accumulatorSecond += (double)values[i] - mean;
			}

			variance = (accumulatorFirst - Math.pow(accumulatorSecond, 2.0D) / (double)values.length) / (double)(values.length - 1);
		}

		return variance;
	}
}

