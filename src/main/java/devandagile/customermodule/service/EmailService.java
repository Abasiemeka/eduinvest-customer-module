package devandagile.customermodule.service;

import devandagile.customermodule.model.dto.SimpleMailDTO;

public interface EmailService {
	void sendSimpleMailMessage(SimpleMailDTO simpleMailDto);
}
