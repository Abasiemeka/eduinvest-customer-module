package devandagile.customermodule.model.dto;

import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Validated
@Builder
public record SimpleMailDTO(
				String to,
				String subject,
				String text
				) {
}
