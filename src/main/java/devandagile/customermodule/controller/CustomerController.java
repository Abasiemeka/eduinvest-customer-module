package devandagile.customermodule.controller;

import devandagile.customermodule.model.dto.*;
import devandagile.customermodule.model.entity.Child;
import devandagile.customermodule.model.entity.Customer;
import devandagile.customermodule.model.enums.Gender;
import devandagile.customermodule.service.CustomerService;
import devandagile.customermodule.service.EmailService;
import devandagile.customermodule.service.EmailServiceOAuth;
import devandagile.customermodule.service.VerificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer")
@Validated
public class CustomerController {

	private final Environment env;
	private final EmailService emailService;
	private final EmailServiceOAuth emailServiceOAuth;
	private final CustomerService customerService;
	private final VerificationService verificationService;
	private final PasswordEncoder passwordEncoder;

	public CustomerController(Environment env,
	                          EmailService emailService, EmailServiceOAuth emailServiceOAuth,
	                          CustomerService customerService,
	                          VerificationService verificationService,
	                          PasswordEncoder passwordEncoder) {
		this.env = env;
		this.emailService = emailService;
		this.emailServiceOAuth = emailServiceOAuth;
		this.customerService = customerService;
		this.verificationService = verificationService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/signup")
	public ResponseEntity<String> signup() {
		return ResponseEntity.status(HttpStatus.FOUND).body("Welcome to EduInvest Signup Page");
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@Valid @RequestBody SignupDTO customer) {
		// Encode password, create customer send verification email.
		return customerService.signup(customer, passwordEncoder.encode(customer.password()));
	}

	@GetMapping("/verify-mail")
	public ResponseEntity<String> confirmEmail(@RequestParam("vtoken") @NotNull String vtoken){
		return customerService.verifyCustomerEmail(vtoken);
	}

	@GetMapping("/login")
	public ResponseEntity<String> login() {
		return ResponseEntity.status(HttpStatus.OK).body("Please login to continue.");
	}

	@PostMapping("/login")
	public ResponseEntity<Customer> login(@Validated @RequestBody LoginDTO loginDTO) {
		return null;
	}

	@GetMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword() {
		return null;
	}

	@GetMapping("/{id}/add-child")
	public ResponseEntity<String> addChild() {
		return null;
	}

	@PostMapping("/{id}/Select-gender")
	public ResponseEntity<String> selectChildGender(@Validated @RequestAttribute Gender gender) {
		//Create new child and assign gender.
		//In customer service, do;
		Child child = Child.builder().gender(gender).build();
		return null;
	}

	@GetMapping("/{id}/update-child-details")
	public ResponseEntity<ChildDetailsDTO> updateChild(){
		return null;
	}

	@PostMapping("/{id}/update-child-details")
	public ResponseEntity<GuardianDetailsDTO> updateChild(@RequestBody ChildDetailsDTO childDetailsDTO){
		return null;
	}

	@PostMapping("/{id}/update-guardian-details")
	public ResponseEntity<String> updateChild(@RequestBody GuardianDetailsDTO guardianDetailsDTO){
		return null;
	}

//	@PutMapping("/{id}")
//	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
//		Customer updatedCustomer = customerService.updateCustomer(id, customer);
//		if (updatedCustomer != null) {
//			return ResponseEntity.ok(updatedCustomer);
//		} else {
//			return ResponseEntity.notFound().build();
//		}
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
//
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

