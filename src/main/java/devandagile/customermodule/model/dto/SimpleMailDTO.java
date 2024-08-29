package devandagile.customermodule.model.dto;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Validated
@Builder
public record SimpleMailDTO(
		Email to,
		String subject,
		String text
				) {
}
