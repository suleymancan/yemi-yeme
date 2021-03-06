package com.suleymancanblog.yemiyeme.clickbait;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created on April, 2019
 *
 * @author suleymancan
 */
@RestController
@RequestMapping("/api/yemi-yeme")
@AllArgsConstructor
@Slf4j
public class ClickbaitRestController {

	private final ClickbaitService clickbaitService;

	@CrossOrigin
	@GetMapping
	public String test(@RequestParam String source) {
		final PrizmaFeature prizmaFeature = clickbaitService.createNewsFeature(source);
		final NewsLabel predict = clickbaitService.predict(prizmaFeature);
		return predict.getNewsLabel();
	}

	@ExceptionHandler(Exception.class)
	public void handleException(Exception e) {
		log.error(e.getMessage(), e);
	}

}
