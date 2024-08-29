package devandagile.customermodule.service;

import jakarta.validation.constraints.Email;

public interface VerificationService {
	String createVerificationAndGetToken(Email email, Integer validityDuration);
}
