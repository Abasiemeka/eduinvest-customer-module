package devandagile.customermodule.model.dto;

import lombok.Builder;
import org.springframework.validation.annotation.Validated;

@Builder
@Validated
public record PaystackPaymentRequestDTO(String email,
                                        int amount) {}
