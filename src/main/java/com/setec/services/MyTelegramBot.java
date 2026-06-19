package com.setec.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import lombok.Data;

@Data
@Service
// MytelegramBot is a service class that handles sending messages to Telegram
// using the Telegram Bot API.
// It uses the @Value annotation to inject the token and chat_id from the
// application properties.
// The message method sends a message to the specified chat_id using the
// TelegramBot API.
public class MyTelegramBot {
	@Value("${token}")
	private String token;
	@Value("${chat_id}")
	private long chat_id;

	// TelegramBot api for sending message to telegram
	private TelegramBot bot;

	// Method to send a message to Telegram using the Telegram Bot API
	public SendResponse message(String text) {
		if (bot == null) {
			bot = new TelegramBot(token);
		}

		SendResponse sent = bot.execute(new SendMessage(chat_id, text));
		return sent;
	}
}
