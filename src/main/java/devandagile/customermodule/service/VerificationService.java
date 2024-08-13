package devandagile.customermodule.service;

public interface VerificationService {
	String createVerificationAndGetToken(String email, Integer validityDuration);
}
