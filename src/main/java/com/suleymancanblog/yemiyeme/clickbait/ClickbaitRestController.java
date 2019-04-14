package com.suleymancanblog.yemiyeme.clickbait;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on April, 2019
 *
 * @author suleymancan
 */
@RestController
@RequestMapping("/api/yemi-yeme")
@AllArgsConstructor
public class ClickbaitRestController {

	private final ClickbaitService clickbaitService;

	@PostMapping
	public ResponseEntity<?> predictClickbait(@RequestBody NewsFeatures newsFeatures){
		try {
			final NewsLabel predict = clickbaitService.predict(newsFeatures);
			return ResponseEntity.ok(predict);
		}
		catch (Exception e){
			return ResponseEntity.badRequest().build();
		}
	}
}
