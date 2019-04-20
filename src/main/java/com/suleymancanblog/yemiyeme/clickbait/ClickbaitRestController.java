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
		final PrizmaFeature prizmaFeature = clickbaitService.createNewsFeature("Telefon sahiplerine çok önemli uyarı: Bugünden itibaren kullandığınız telefonlar artık...");
		final NewsLabel predict = clickbaitService.predict(prizmaFeature);
		return predict.toString();
	}

	@PostMapping
	public ResponseEntity<?> predictClickbait(@RequestBody PrizmaFeature prizmaFeature){
		try {
			final NewsLabel predict = clickbaitService.predict(prizmaFeature);
			return ResponseEntity.ok(predict);
		}
		catch (Exception e){
			return ResponseEntity.badRequest().build();
		}
	}
}
