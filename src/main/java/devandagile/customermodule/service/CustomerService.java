package devandagile.customermodule.service;

import devandagile.customermodule.model.dto.SignupDTO;
import devandagile.customermodule.model.entity.Customer;

public interface CustomerService {
	Customer getCustomerById(Long id);

	Customer getCustomerByEmail(String email);

	Customer signup(SignupDTO customer);

	void verifyCustomerEmail(String vtoken);
}
