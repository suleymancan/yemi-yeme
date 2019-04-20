package com.suleymancanblog.yemiyeme.clickbait;

import com.suleymancanblog.yemiyeme.prizma.NewsFeature;
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
		final NewsFeature newsFeature = clickbaitService.createNewsFeature("Ali Koç’tan tarihi itiraf: Utanıyorum");
		final NewsLabel predict = clickbaitService.predict(newsFeature);
		return predict.toString();
	}

	@PostMapping
	public ResponseEntity<?> predictClickbait(@RequestBody NewsFeature newsFeature){
		try {
			final NewsLabel predict = clickbaitService.predict(newsFeature);
			return ResponseEntity.ok(predict);
		}
		catch (Exception e){
			return ResponseEntity.badRequest().build();
		}
	}
}
