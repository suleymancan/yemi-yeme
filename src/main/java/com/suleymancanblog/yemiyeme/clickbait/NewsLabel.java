package com.suleymancanblog.yemiyeme.clickbait;

/**
 * Created on April, 2019
 *
 * @author suleymancan
 */
enum NewsLabel {

	CLICKBAIT("CLICKBAIT"), NOT_CLICKBAIT("NOT CLICKBAIT");

	private final String newsLabel;

	NewsLabel(String newsLabel) {
		this.newsLabel = newsLabel;
	}

	public String getNewsLabel() {
		return newsLabel;
	}}
