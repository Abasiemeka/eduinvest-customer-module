package devandagile.customermodule.model.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import org.springframework.lang.NonNull;

@Builder
public record VerificationMailDTO(
		@NonNull String firstName,
		@NonNull Email email,
		@NonNull String verificationToken
) {
}
