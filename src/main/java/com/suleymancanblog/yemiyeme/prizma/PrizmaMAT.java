package com.suleymancanblog.yemiyeme.prizma;

/**
 * Codes in this class belong to the prizma project.
 * https://code.google.com/archive/p/prizma-text-classification/
 */
public class PrizmaMAT {

	static double variance(int[] values, double mean) {
		double variance = 0.0D / 0.0;
		if (values.length == 1) {
			variance = 0.0D;
		}
		else {
			if (values.length <= 1) {
				throw new IllegalArgumentException("Length of values[] must be greater than 0");
			}

			double accumulatorFirst = 0.0D;
			double accumulatorSecond = 0.0D;

			for (int i = 0; i < values.length; ++i) {
				accumulatorFirst += Math.pow((double) values[i] - mean, 2.0D);
				accumulatorSecond += (double) values[i] - mean;
			}

			variance = (accumulatorFirst - Math.pow(accumulatorSecond, 2.0D) / (double) values.length) / (double) (values.length - 1);
		}

		return variance;
	}
}

