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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "booked")
public class Booked {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 100, message = "Name must be 2 to 100 characters")
	@Column(nullable = false)
	private String name;

	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[0-9+]{9,12}$", message = "Phone must be 9 to 12 digits")
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@NotBlank(message = "Email is required")
	@Email(regexp = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$", message = "Please enter a valid email (e.g. name@example.com)")
	@Column(nullable = false)
	private String email;

	@NotBlank(message = "Date is required")
	@Column(nullable = false)
	private String date;

	@NotBlank(message = "Time is required")
	@Column(nullable = false)
	private String time;

	@NotNull(message = "Please select number of persons")
	@Min(value = 1, message = "Please select at least 1 person")
	@Column(nullable = false)
	private Integer person;

	@Column(name = "booked_at")
	private LocalDateTime bookedAt;

	@PrePersist
	public void prePersist() {
		this.bookedAt = LocalDateTime.now();
	}
}
