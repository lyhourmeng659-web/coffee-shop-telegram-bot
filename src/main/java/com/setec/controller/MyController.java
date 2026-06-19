package com.setec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.setec.entities.Booked;
import com.setec.entities.Contact;
import com.setec.repos.BookedRepo;
import com.setec.services.ContactService;
import com.setec.services.MyTelegramBot;

import jakarta.validation.Valid;

@Controller
public class MyController {
	// http://localhost:8080/home
	// Home Controller
	@GetMapping({ "/", "/home" })
	public String home(Model mod) {
		if (!mod.containsAttribute("booked")) {
			mod.addAttribute("booked", new Booked());
		}
		return "index";
	}

	// About Controller
	@GetMapping("/about")
	public String about() {
		return "about";
	}

	// Service Controller
	@GetMapping("/service")
	public String service() {
		return "service";
	}

	// Menu Controller
	@GetMapping("/menu")
	public String menu() {
		return "menu";
	}

	// Reservation controller method that adds a Booked object to the model and
	// returns the reservation view.
	@GetMapping("/reservation")
	public String reservation(Model mod) {
		// Use flash-carried booked if exists, else new empty one
		if (!mod.containsAttribute("booked")) {
			mod.addAttribute("booked", new Booked());
		}
		// Booked booked = new Booked();
		// mod.addAttribute("booked", booked);
		return "reservation";
	}

	// Testimonial Controller
	@GetMapping("/testimonial")
	public String testimonial() {
		return "testimonial";
	}

	// Contact Controller
	@GetMapping("/contact")
	public String contact(Model mod) {
		if (!mod.containsAttribute("contact")) {
			mod.addAttribute("contact", new Contact());
		}
		return "contact";
	}

	@Autowired
	private ContactService contactService;

	@PostMapping("/contact")
	public String submitContact(@Valid @ModelAttribute Contact contact, RedirectAttributes redireactAttributes,
			BindingResult bindingResult) {
		
		// Server-side validation failed
		if (bindingResult.hasErrors()) {
			String errors = bindingResult.getFieldErrors()
					.stream().map(err -> "• " + err.getDefaultMessage())
					.collect(java.util.stream.Collectors.joining("<br>"));
			redireactAttributes.addFlashAttribute("errorMessage", errors);
			redireactAttributes.addFlashAttribute("contact", contact);
			return "redirect:/contact";
		}
		
		try {
			contactService.saveAndNotify(contact);
			redireactAttributes.addFlashAttribute("successMessage", "Your message has been sent successfully!");
		} catch (Exception e) {
			e.printStackTrace();
			redireactAttributes.addFlashAttribute("errorMessage", "Failed to send message. Please try again.");
		}
		return "redirect:/contact";
	}

	// Success Controller
	// Autowired annotation is used to inject the BookedRepo and MyTelegramBot
	// dependencies into the MyController class.
	@Autowired
	private BookedRepo bookedRepo;

	// Autowired annotation is used to inject the MyTelegramBot dependency into the
	// MyController class.
	@Autowired
	private MyTelegramBot bot;

	@PostMapping("/success")
	public String success(@Valid @ModelAttribute Booked booked, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {

		// If validation fails — go back to reservation with errors
		if (bindingResult.hasErrors()) {
			// Collect all error messages
			String erros = bindingResult.getFieldErrors().stream().map(err -> "• " + err.getDefaultMessage())
					.collect(java.util.stream.Collectors.joining("\n"));

			redirectAttributes.addFlashAttribute("errorMessage", erros);
			redirectAttributes.addFlashAttribute("booked", booked);
			return "redirect:/reservation";
		}

		try {
			bookedRepo.save(booked);
			String text = "📌 New Reservation\n\n" + "👤 Name  : " + booked.getName() + "\n" + "📞 Phone : "
					+ booked.getPhoneNumber() + "\n" + "📧 Email   : " + booked.getEmail() + "\n" + "📅 Date    : "
					+ booked.getDate() + "\n" + "⏰ Time    : " + booked.getTime() + "\n" + "👥 Person  : "
					+ booked.getPerson();
			bot.message(text);
			redirectAttributes.addFlashAttribute("booked", booked);
			redirectAttributes.addFlashAttribute("successMessage", "Your table has been booked successfully!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to book table. Please try again.");
		}
		return "redirect:/success";
	}

	@GetMapping("/success")
	public String successPage() {
		return "success";
	}
}
