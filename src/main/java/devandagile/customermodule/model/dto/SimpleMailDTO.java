package devandagile.customermodule.model.dto;

import lombok.Builder;

@Builder
public record SimpleMailDTO(String to,
                            String subject,
                            String text){
}
