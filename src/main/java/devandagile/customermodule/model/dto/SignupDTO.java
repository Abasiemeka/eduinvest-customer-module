package devandagile.customermodule.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Builder
public record SignupDTO (
				@NotBlank(message = "First Name is required")
				String firstName,

				@NotBlank(message = "Last Name is required")
				String lastName,

				@NotBlank(message = "Phone number is required")
				String phone,

				@Getter
				@Size(max = 100)
				@NotBlank(message = "Email Name is required")
				@Email(message = "A valid email is required")
				@Column(unique = true)
				String email,

				@NotNull
				@Setter
				@NotBlank(message = "Password Name is required")
				@Size(max = 20, message = "Password must be less than or equal to 20 characters")
				@Size(min = 8, message = "Password must be more than or equal to 8 characters")
				@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-+=]).{8,}$", message = "Password must contain at least 8 characters, one digit, one lowercase letter, one uppercase letter, and one special character")
				String password,

				String referralCode
				) {
}
