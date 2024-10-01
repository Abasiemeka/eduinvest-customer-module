package devandagile.customermodule.repository;

import devandagile.customermodule.model.entity.DeletedCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedCustomerRepository extends JpaRepository<DeletedCustomer, Long> {
}