package com.setec.entities;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contacts")
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 100, message = "Name must be 2 to 100 characters")
	@Column(nullable = false)
	private String name;

	@NotBlank(message = "Email is required")
	@Email(regexp = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$", message = "Please enter a valid email (e.g. name@example.com)")
	@Column(nullable = false)
	private String email;

	@NotBlank(message = "Subject is required")
	@Size(min = 2, max = 200, message = "Subject must be 2 to 200 characters")
	@Column(nullable = false)
	private String subject;

	@NotBlank(message = "Message is required")
	@Size(min = 5, max = 1000, message = "Message must be 5 to 1000 characters")
	@Column(nullable = false, columnDefinition = "TEXT")
	private String message;

	@Column(name = "submitted_at")
	private LocalDateTime submittedAt;

	@PrePersist
	public void prePersist() {
		this.submittedAt = LocalDateTime.now();
	}
}
