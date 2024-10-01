package devandagile.customermodule.controller;

import devandagile.customermodule.model.dto.*;
import devandagile.customermodule.model.entity.Child;
import devandagile.customermodule.model.enums.Gender;
import devandagile.customermodule.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping({"/v1/customer", "/v1/customer/"})
@Validated
public class CustomerController {

	private final CustomerService customerService;
	private final PasswordEncoder passwordEncoder;

	public CustomerController(CustomerService customerService,
	                          PasswordEncoder passwordEncoder) {
		this.customerService = customerService;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/signup")
	public ResponseEntity<String> signup() {
		return ResponseEntity.status(HttpStatus.FOUND).body("Welcome to EduInvest Signup Page");
	}

	@PostMapping("/signup")
	public GenericResponse signup(@Valid @RequestBody SignupDTO customerSignupDTO) {
		return customerService.signup(customerSignupDTO, passwordEncoder.encode(customerSignupDTO.password()));
	}

	@GetMapping("/verify-mail")
	public GenericResponse verifyEmail(@RequestParam("vtoken") @NotNull String vtoken){
		return customerService.verifyCustomerEmail(vtoken);
	}

	@GetMapping("/login")
	public ResponseEntity<String> login() {
		return ResponseEntity.status(HttpStatus.OK).body("Welcome to EduInvest Login Page");
	}

	@PostMapping("/login")
	public GenericResponse login(@Validated @RequestBody LoginDTO loginDTO) {
		return Objects.isNull(loginDTO)
				?
				customerService.loadUser(authorizationHeader.substring(7))
				:
		customerService.login(loginDTO.UsernameOrEmail(), passwordEncoder.encode(loginDTO.password()));
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

