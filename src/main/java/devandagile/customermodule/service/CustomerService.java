package devandagile.customermodule.service;

import devandagile.customermodule.model.dto.SignupDTO;
import devandagile.customermodule.model.entity.Customer;
import org.springframework.http.ResponseEntity;

public interface CustomerService {

	Customer getCustomerById(Long id);

	Customer getCustomerByEmail(String email);

	Customer signup(SignupDTO customer);

	ResponseEntity<String> verifyCustomerEmail(String vtoken);

	boolean userExists(String email);
}
