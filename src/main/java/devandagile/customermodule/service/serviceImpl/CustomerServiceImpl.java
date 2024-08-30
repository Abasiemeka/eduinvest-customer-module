package devandagile.customermodule.service.serviceImpl;

import devandagile.customermodule.config.exception.Exceptions.UserNotFoundException;
import devandagile.customermodule.model.dto.SignupDTO;
import devandagile.customermodule.model.dto.SimpleMailDTO;
import devandagile.customermodule.model.dto.VerificationMailDTO;
import devandagile.customermodule.model.entity.Customer;
import devandagile.customermodule.model.entity.UserAccount;
import devandagile.customermodule.model.entity.Verification;
import devandagile.customermodule.model.enums.Authority;
import devandagile.customermodule.model.enums.Roles;
import devandagile.customermodule.repository.CustomerRepository;
import devandagile.customermodule.repository.VerificationRepository;
import devandagile.customermodule.service.CustomerService;
import devandagile.customermodule.service.EmailServiceOAuth;
import devandagile.customermodule.service.VerificationService;
import jakarta.validation.constraints.Email;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private final EmailServiceOAuth emailServiceOAuth;
	private final CustomerRepository customerRepository;
	private final VerificationService verificationService;
	private final VerificationRepository verificationRepository;

	public CustomerServiceImpl(EmailServiceOAuth mailer, CustomerRepository customerRepository, VerificationService verificationService,
	                           VerificationRepository verificationRepository) {
		this.emailServiceOAuth = mailer;
		this.customerRepository = customerRepository;
		this.verificationService = verificationService;
		this.verificationRepository = verificationRepository;
	}

	@Override
	public Customer getCustomerById(Long id) throws UserNotFoundException {
		return customerRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("No such customer."));
	}

	@Override
	public Customer getCustomerByEmailOrNull(String email) {
		return customerRepository.findCustomerByEmailIgnoreCase(email).orElse(null);
	}

	@Override
	public ResponseEntity<String> signup(SignupDTO customer, String encodedPassword) {
		if (customerExists(customer.email())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Customer with email already exists");
		}

		Customer createdCustomer = customerRepository.save(
						Customer
								.builder()
								.firstName(customer.firstName())
								.lastName(customer.lastName())
								.phone(customer.phone())
								.email(customer.email())
								.password(encodedPassword)
								.account(UserAccount
										.builder()
										.username(customer.email())
										.role(Roles.ROLE_USER)
										.authority(Authority.ROLE_USER)
										.build())
								.referralCode(customer.referralCode())
								.build()
				);

		logger.info("Customer created successfully as follows: " +
						"Customer name = {}, Customer email = {}",
				createdCustomer.getFirstName() + " " + createdCustomer.getLastName(), createdCustomer.getEmail());

		// Generate verification token and send email
		emailServiceOAuth.sendSimpleVerificationEMail(
				VerificationMailDTO
						.builder()
						.firstName(createdCustomer.getFirstName())
						.email(createdCustomer.getEmail())
						.verificationToken(
								verificationService.createVerificationAndGetToken(customer.email(), 10))
						.build());

		logger.info("Verification email sent to customer with email = {}", createdCustomer.getEmail());

		return ResponseEntity.status(HttpStatus.CREATED).body("Account created. Awaiting email verification.");
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
		emailServiceOAuth.sendEmail(SimpleMailDTO
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
		emailServiceOAuth.sendEmail(SimpleMailDTO
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
	public boolean customerExists(String email) {
		return getCustomerByEmailOrNull(email) != null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//fixme: refactor to replace find by email... pass email here to act as the default find method
		Customer customer = new Customer();
		return customer.getAccount();
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
				.getUserInfoEndpoint().getUserNameAttributeName();

		Map<String, Object> attributes = oAuth2User.getAttributes();

		UserAccount userAccount = this.processOAuth2User(registrationId, attributes, userNameAttributeName);

		return new DefaultOAuth2User(
				userAccount.getAuthorities(),
				attributes,
				userNameAttributeName);
	}

	private UserAccount processOAuth2User(String registrationId, Map<String, Object> attributes, String userNameAttributeName) {
		String email = String.valueOf(attributes.get("email"));
		Customer customer = customerRepository.findCustomerByEmailIgnoreCase(email)
				.orElseGet(() -> createNewCustomer(registrationId, attributes));

		return customer.getAccount();
	}

	private Customer createNewCustomer(String registrationId, Map<String, Object> attributes) {
		Customer customer = new Customer();
		customer.setEmail(String.valueOf(attributes.get("email")));
		customer.setFirstName((String) attributes.get("given_name"));
		customer.setLastName((String) attributes.get("family_name"));

		UserAccount account = new UserAccount();
		// Assuming AuthProvider is an enum that needs to be imported
		// account.setProvider(AuthProvider.valueOf(registrationId.toUpperCase()));
		account.setProviderId((String) attributes.get("sub"));
		// Assuming Role is an enum that needs to be imported
		// account.setRole(Role.CUSTOMER);
		// account.setEnabled(true);

		customer.setAccount(account);

		return customerRepository.save(customer);
	}
}
