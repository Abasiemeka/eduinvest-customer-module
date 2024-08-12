package devandagile.customermodule.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record AgentCustomerDTO(@NotBlank Long id,
                               @NotBlank String name,
                               @NotBlank @Email String email,
                               @NotBlank LocalDate registrationDate){

}