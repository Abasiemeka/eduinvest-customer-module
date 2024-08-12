package devandagile.customermodule.repository;

import devandagile.customermodule.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Optional<Customer> findCustomerByEmailIgnoreCase(@NonNull String email);
}