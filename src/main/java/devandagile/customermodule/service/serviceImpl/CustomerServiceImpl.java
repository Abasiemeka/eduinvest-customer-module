package devandagile.customermodule.service.serviceImpl;

import devandagile.customermodule.model.dto.SignupDTO;
import devandagile.customermodule.model.dto.SimpleMailDTO;
import devandagile.customermodule.model.entity.Customer;
import devandagile.customermodule.model.entity.Verification;
import devandagile.customermodule.repository.VerificationRepository;
import devandagile.customermodule.repository.CustomerRepository;
import devandagile.customermodule.service.CustomerService;
import devandagile.customermodule.service.EmailServiceOAuth;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private final EmailServiceOAuth mailer;
	private final CustomerRepository customerRepository;
	private final VerificationRepository verificationRepository;

	public CustomerServiceImpl(EmailServiceOAuth mailer, CustomerRepository customerRepository,
	                           VerificationRepository verificationRepository) {
		this.mailer = mailer;
		this.customerRepository = customerRepository;
		this.verificationRepository = verificationRepository;
	}

	@Override
	public Customer getCustomerById(Long id) {
		return null;
	}

	@Override
	public Customer getCustomerByEmail(String email) {
		return customerRepository.findCustomerByEmailIgnoreCase(email).orElse(null);
	}

	@Override
	public Customer signup(SignupDTO customer) {
		Customer newCustomer = Customer
				.builder()
				.firstName(customer.getFirstName())
				.lastName(customer.getLastName())
				.phone(customer.getPhone())
				.email(customer.getEmail())
				.passwordHash(customer.getPassword())
				.referralCode(customer.getReferralCode())
				.build();

		customerRepository.save(newCustomer);
		return newCustomer;
	}

	@SneakyThrows
	@Override
	public ResponseEntity<String> verifyCustomerEmail(String vtoken) {
		Verification verification = verificationRepository.findByToken(vtoken)
				.orElseThrow(AuthenticationException::new);

		return (verification.isExpired())
				? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(discardRegistrationAttempt(verification))
				: ResponseEntity.status(HttpStatus.ACCEPTED).body(enableVerifiedCustomer(verification));
	}

	private String enableVerifiedCustomer(Verification verification) {
		String emailAddress = verification.getEmail();
		customerRepository.findCustomerByEmailIgnoreCase(emailAddress).orElseThrow().setEnabled(true);
		verificationRepository.delete(verification);
		mailer.sendEmail(SimpleMailDTO
				.builder()
				.to(verification.getEmail())
				.subject("Email Verified.")
				.text("Your email address was successfully verified and your registration with EduInvest is complete. Kindly login to continue.")
				.build());
		logger.info("Email verification successful for {}. Customer enabled and verification object dismissed.", emailAddress);
		return "Verification Successful. Customer registered successfully and notified by email.";
	}

	private String discardRegistrationAttempt(Verification verification) {
		String emailAddress = verification.getEmail();
		customerRepository.deleteCustomerByEmail(emailAddress);
		logger.info("Customer with email {} has been deleted. Verification expired.", emailAddress);
		mailer.sendEmail(SimpleMailDTO
				.builder()
				.to(emailAddress)
				.subject("Email Verification Failed.")
				.text("EduInvest could not verify your email address as the time limit has expired." +
						" Kindly register again.")
				.build());
		logger.info("Email notification sent to {} requesting re-registration.", emailAddress);
		verificationRepository.delete(verification);
		logger.info("Verification instance for {} has been deleted", emailAddress);
		return "Verification failed. Token was expired. Registration for Customer with email " + emailAddress + " has been reversed.";
	}

	@Override
	public boolean userExists(String email) {
		return getCustomerByEmail(email) != null;
	}
}
