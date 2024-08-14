package devandagile.customermodule.model.entity;

import devandagile.customermodule.enums.CustomerType;
import devandagile.customermodule.model.entity.baseEntity.Person;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Setter
@SuperBuilder
public class Customer extends Person {
	private String referralCode;
	private CustomerType relationship;
	private LocalDate registrationDate;

	@OneToMany
	@JoinColumn(name = "investment_details", referencedColumnName = "id")
	private HashSet<InvestmentDetails> investmentDetailsSet;

	@OneToMany
	@JoinColumn(name = "child_id", referencedColumnName = "id")
	private HashSet<Child> childSet;

	@OneToMany
	@JoinColumn(name = "product-id", referencedColumnName = "id")
	private HashSet<Product> productSet;
}
