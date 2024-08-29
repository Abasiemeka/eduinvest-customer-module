package devandagile.customermodule.service;

import devandagile.customermodule.model.dto.SimpleMailDTO;
import devandagile.customermodule.model.dto.VerificationMailDTO;

public interface EmailServiceOAuth {

	void sendEmail(SimpleMailDTO simpleMailDTO);

	void sendSimpleVerificationEMail(VerificationMailDTO verificationMailDTO);
}
