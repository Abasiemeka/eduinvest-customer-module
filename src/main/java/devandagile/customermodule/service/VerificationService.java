package devandagile.customermodule.service;

public interface VerificationService {
	String getVerificationToken(String email, Integer tokenLength, Integer validityDuration);
}
