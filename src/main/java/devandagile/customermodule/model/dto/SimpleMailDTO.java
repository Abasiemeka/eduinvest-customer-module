package devandagile.customermodule.model.dto;

public record SimpleMailDTO(String to,
                            String from,
                            String subject,
                            String text){
}
