package devandagile.customermodule.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Validated
@Builder
public record AgentCustomerDTO(
				@NotBlank Long id,
				@NotBlank String name,
				@NotBlank @Email String email,
				@NotBlank LocalDate registrationDate
				) {
}