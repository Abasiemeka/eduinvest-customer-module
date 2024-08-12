package devandagile.customermodule.controller;

import devandagile.customermodule.model.dto.SignupDTO;
import devandagile.customermodule.model.dto.SimpleMailDTO;
import devandagile.customermodule.model.entity.Customer;
import devandagile.customermodule.service.CustomerService;
import devandagile.customermodule.service.EmailService;
import devandagile.customermodule.service.VerificationService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

	private final Environment env;
	private final EmailService emailService;
	private final CustomerService customerService;
	private final VerificationService verificationService;
	private final BCryptPasswordEncoder passwordEncoder;

	public CustomerController(Environment env,
	                          EmailService emailService,
	                          CustomerService customerService,
	                          VerificationService verificationService,
	                          BCryptPasswordEncoder passwordEncoder) {
		this.env = env;
		this.emailService = emailService;
		this.customerService = customerService;
		this.verificationService = verificationService;
		this.passwordEncoder = passwordEncoder;
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody SignupDTO customer) {
		if(customerService.getCustomerByEmail(customer.getEmail()) != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
		}
		else {
			customer.setPassword(passwordEncoder.encode(customer.getPassword()));
			Customer createdCustomer = customerService.signup(customer);

			emailService.sendSimpleMailMessage(
					new SimpleMailDTO(
							createdCustomer.getEmail(),
							env.getProperty("MAIL_USERNAME"),
							"Confirm email address to complete EduInvest Registration",
							"Hello " + customer.getFirstName()
									+", kindly click on the link below to complete your EduInvest registration.\n"
									+"https://127.0.0.1:7075/v1/customer/verify-mail?vtoken="
									+ verificationService.getVerificationToken(createdCustomer.getEmail(), 15, 10)));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body("Account created.");
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
//
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

