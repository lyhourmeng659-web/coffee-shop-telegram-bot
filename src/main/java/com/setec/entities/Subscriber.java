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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscribers")
public class Subscriber {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Email is required")
	@Email(regexp = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$",
		message = "Please enter a valid email (e.g. name@example.com)"
	)
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(name = "source")
	private String source; 
	
	@Column(name = "subscribed_at")
	private LocalDateTime subscribedAt;
	
	@PrePersist
	public void prePersist() {
		this.subscribedAt = LocalDateTime.now();
	}
}
