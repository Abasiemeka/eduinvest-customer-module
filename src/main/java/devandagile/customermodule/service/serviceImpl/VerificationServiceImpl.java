package devandagile.customermodule.service.serviceImpl;

import devandagile.customermodule.model.entity.Verification;
import devandagile.customermodule.repository.VerificationRepository;
import devandagile.customermodule.service.VerificationService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VerificationServiceImpl implements VerificationService {

	private final VerificationRepository verificationRepository;

	@Override
	public String createVerificationAndGetToken(String email, Integer validityDuration) {
		Verification verification = new Verification(email, 10);
		verificationRepository.save(verification);
		return verification.getToken();
	}
}
