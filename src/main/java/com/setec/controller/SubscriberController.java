package com.setec.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.setec.services.SubscriberService;

@RestController
@RequestMapping("/subscribe")
public class SubscriberController {
	private final SubscriberService subscriberService;

	public SubscriberController(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> subscribe(@RequestParam("email") String email,
			@RequestParam("source") String source) {

		String result = subscriberService.subscribe(email, source);

		return switch (result) {
		case "SUCCESS" ->
			ResponseEntity.ok(Map.of("status", "SUCCESS", "message", "You have successfully subscribed!"));
		case "DUPLICATE" -> ResponseEntity.badRequest()
				.body(Map.of("status", "DUPLICATE", "message", "This email is already subscribed."));
		case "INVALID" -> ResponseEntity.badRequest()
				.body(Map.of("status", "INVALID", "message", "Please enter a valid email (e.g. name@example.com)."));
		default -> ResponseEntity.internalServerError()
				.body(Map.of("status", "ERROR", "message", "Something went wrong. Please try again."));
		};
	}
}
