package devandagile.customermodule.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SignupDTO {
	@NotBlank(message = "First Name is required")
	private String firstName;

	@NotBlank(message = "Last Name is required")
	private String lastName;

	@NotBlank(message = "Phone number is required")
	private String phone;

	@Size(max = 100)
	@NotBlank(message = "Email Name is required")
	@Email(message = "A valid email is required")
	@Column(unique = true)
	private String email;

	@NotNull
	@Setter
	@NotBlank(message = "Password Name is required")
	@Size(max = 20, message = "Password must be less than or equal to 20 characters")
	@Size(min = 8, message = "Password must be more than or equal to 8 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-+=]).{8,}$", message = "Password must contain at least 8 characters, one digit, one lowercase letter, one uppercase letter, and one special character")
	private String password;

	private String referralCode;
}
