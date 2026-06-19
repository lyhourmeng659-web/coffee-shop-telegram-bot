package com.setec.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.setec.entities.Subscriber;
import com.setec.repos.SubscriberRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.Data;

@Data
@Service
public class SubscriberService {
	private final SubscriberRepo subscriberRepo;
	private final MyTelegramBot myTelegramBot;
	
	public SubscriberService(SubscriberRepo subscriberRepo, MyTelegramBot myTelegramBot) {
		this.subscriberRepo = subscriberRepo;
		this.myTelegramBot = myTelegramBot;
	}
	
	public String subscribe(String email, String source) {
		// Basic blank check
		if (email == null || email.isBlank()) {
			return "INVALID";
		}
		
		// Server-side validate email format using Bean Validation
		Subscriber subscriber = new Subscriber();
		subscriber.setEmail(email);
		subscriber.setSource(source);
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Subscriber>> violations = validator.validateProperty(subscriber, "email");
		if (!violations.isEmpty()) {
			return "INVALID";
		}
		
		// Check duplicate email
		if (subscriberRepo.findByEmail(email).isPresent()) {
			return "DUPLICATE";
		}
		
		// Save to MySQL
		subscriberRepo.save(subscriber);
		
        // Notify admin via Telegram
        String msg = "🔔 New Newsletter Subscriber\n\n" +
                "📧 Email    : " + email + "\n" +
                "📍 Source : " + source;
        
        myTelegramBot.message(msg);
		return "SUCCESS";
	}
}
