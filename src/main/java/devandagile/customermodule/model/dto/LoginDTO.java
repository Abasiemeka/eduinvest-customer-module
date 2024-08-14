package devandagile.customermodule.model.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Builder
@Getter
public class LoginDTO {
	@NotBlank(message = "Provide username or email to login.")
	private String UsernameOrEmail;

	@NotNull
	@Setter
	@NotBlank(message = "Password Name is required")
	@Size(max = 20, message = "Password must be less than or equal to 20 characters")
	@Size(min = 8, message = "Password must be more than or equal to 8 characters")
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()-+=]).{8,}$", message = "Password must contain at least 8 characters, one digit, one lowercase letter, one uppercase letter, and one special character")
	private String password;
}
