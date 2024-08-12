package devandagile.customermodule.service.serviceImpl;

import devandagile.customermodule.model.dto.SignupDTO;
import devandagile.customermodule.model.entity.Customer;
import devandagile.customermodule.repository.CustomerRepository;
import devandagile.customermodule.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
	private final CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
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
		return null;
	}

	@Override
	public void verifyCustomerEmail(String vtoken) {

	}
}
