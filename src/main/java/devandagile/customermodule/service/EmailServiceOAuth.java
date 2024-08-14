package devandagile.customermodule.service;

import devandagile.customermodule.model.dto.SimpleMailDTO;

public interface EmailServiceOAuth {
	void sendEmail(SimpleMailDTO simpleMailDTO);
}
