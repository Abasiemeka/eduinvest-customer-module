package devandagile.customermodule.repository;

import devandagile.customermodule.model.entity.Customer;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findCustomerByEmailIgnoreCase(@NonNull String email);
	void deleteByEmail(@NotBlank String email);

	void deleteCustomerByEmail(String email);
}