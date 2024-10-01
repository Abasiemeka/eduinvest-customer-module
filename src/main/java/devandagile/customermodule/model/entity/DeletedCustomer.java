package devandagile.customermodule.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Entity(name = "deleted_customer")
public class DeletedCustomer extends Customer {
}
