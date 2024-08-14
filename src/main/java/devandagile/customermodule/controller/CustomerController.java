package devandagile.customermodule.controller;

import devandagile.customermodule.config.security.SecurityConfig;
import devandagile.customermodule.model.dto.SignupDTO;
import devandagile.customermodule.model.dto.SimpleMailDTO;
import devandagile.customermodule.model.entity.Customer;
import devandagile.customermodule.service.CustomerService;
import devandagile.customermodule.service.EmailService;
import devandagile.customermodule.service.EmailServiceOAuth;
import devandagile.customermodule.service.VerificationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer")
@Validated
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	private final Environment env;
	private final EmailService emailService;
	private final EmailServiceOAuth emailServiceOAuth;
	private final CustomerService customerService;
	private final VerificationService verificationService;
	private final SecurityConfig securityConfig;

	public CustomerController(Environment env,
	                          EmailService emailService, EmailServiceOAuth emailServiceOAuth,
	                          CustomerService customerService,
	                          VerificationService verificationService,
	                          SecurityConfig securityConfig) {
		this.env = env;
		this.emailService = emailService;
		this.emailServiceOAuth = emailServiceOAuth;
		this.customerService = customerService;
		this.verificationService = verificationService;
		this.securityConfig = securityConfig;
	}

	@GetMapping("/signup")
	public ResponseEntity<String> signup() {
		return ResponseEntity.status(HttpStatus.FOUND).body("Welcome to EduInvest Signup Page");
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@Valid @RequestBody SignupDTO customer) {
		if (customerService.userExists(customer.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
		}

		// Encode password, create customer and log result.
		customer.setPassword(securityConfig.passwordEncoder().encode(customer.getPassword()));
		Customer createdCustomer = customerService.signup(customer);

		logger.info("Customer created successfully as follows: " +
						"Customer name = {}, Customer email = {}",
				createdCustomer.getFirstName()+" "+createdCustomer.getLastName(), createdCustomer.getEmail());

		// Generate verification token and send email
		String verificationToken = verificationService.createVerificationAndGetToken(customer.getEmail(), 10);
		emailServiceOAuth.sendEmail(
				new SimpleMailDTO(
						createdCustomer.getEmail(),
						"Confirm email address to complete EduInvest Registration",
						"Hello " + customer.getFirstName()
								+ ", kindly click on the link below to complete your EduInvest registration.\n"
								+ "https://127.0.0.1:7075/v1/customer/verify-mail?vtoken="
								+ verificationToken)
		);

		return ResponseEntity.status(HttpStatus.CREATED).body("Account created. Awaiting email verification.");
	}

//	@GetMapping("/verify-mail")
//	public ResponseEntity<String> confirmEmail(@PathVariable("vtoken") String vtoken){
//		customerService.verifyCustomerEmail(vtoken);
//		//move the code below to the customer service confirmCustomerEmail method
//
//		if(tokenMail == null) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email verification failed.");
//		}
//		else {
//			customerService.getCustomerByEmail(tokenMail).set
//		}
//		return ResponseEntity.status(HttpStatus.CREATED).body("Account created.");
//	}

//	@GetMapping("/{id}")
//	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
//		Customer customer = customerService.getCustomerById(id);
//		if (customer != null) {
//			return ResponseEntity.ok(customer);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}
//
//	@PostMapping("/signup")
//	public ResponseEntity<Customer> signupe(@RequestBody SignupDTO customer) {
//		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
//		Customer createdCustomer = customerService.signup(customer);
//		return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
//	}
//
//	@PutMapping("/{id}")
//	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
//		Customer updatedCustomer = customerService.updateCustomer(id, customer);
//		if (updatedCustomer != null) {
//			return ResponseEntity.ok(updatedCustomer);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
//		boolean isDeleted = customerService.deleteCustomer(id);
//		if (isDeleted) {
//			return ResponseEntity.noContent().build();
//		} else {
//			return ResponseEntity.notFound().build();
//		}
//	}

}

