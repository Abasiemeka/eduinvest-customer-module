package devandagile.customermodule.service.serviceImpl;

import devandagile.customermodule.model.entity.Verification;
import devandagile.customermodule.model.entity.VerificationRepository;
import devandagile.customermodule.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VerificationServiceImpl implements VerificationService {
	private final VerificationRepository verificationRepository;


	@Override
	public String getVerificationToken(String email, Integer tokenLength, Integer validityDuration) {
		Verification verification = new Verification(email, 15, 10);
		verificationRepository.save(verification);
		return verification.getToken();
	}
}
