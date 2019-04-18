package com.suleymancanblog.yemiyeme.clickbait;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping
	public String test(){
		//clickbaitService.test();
		return "hello";
	}

	@PostMapping
	public ResponseEntity<?> predictClickbait(@RequestBody NewsFeaturesPrizma newsFeaturesPrizma){
		try {
			final NewsLabel predict = clickbaitService.predict(newsFeaturesPrizma);
			return ResponseEntity.ok(predict);
		}
		catch (Exception e){
			return ResponseEntity.badRequest().build();
		}
	}
}
