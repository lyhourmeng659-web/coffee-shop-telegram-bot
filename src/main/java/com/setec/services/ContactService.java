package com.setec.services;

import org.springframework.stereotype.Service;
import com.setec.entities.Contact;
import com.setec.repos.ContactRepo;
import lombok.Data;

@Data
@Service
public class ContactService {
	private final ContactRepo contactRepo;
	private final MyTelegramBot myTelegramBot;
	
	public ContactService(ContactRepo contactRepo, MyTelegramBot myTelegramBot) {
		this.contactRepo = contactRepo;
		this.myTelegramBot = myTelegramBot;
	}
	
	public void saveAndNotify(Contact contact) {
		// Save to MySQL
		contactRepo.save(contact);
		
		// Send Telegram notification to admin
        String msg = "📬 New Contact Message\n\n" +
                "👤 Name        : " + contact.getName() + "\n" +
                "📧 Email         : " + contact.getEmail() + "\n" +
                "📌 Subject     : " +  contact.getSubject() + "\n" +
                "💬 Message   : " + contact.getMessage();
        myTelegramBot.message(msg); 
	}
}
