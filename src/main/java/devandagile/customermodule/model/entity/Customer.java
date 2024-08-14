package devandagile.customermodule.model.entity;

import devandagile.customermodule.model.enums.CustomerType;
import devandagile.customermodule.model.entity.baseEntity.Person;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;

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
	@Embedded
	private HashSet<InvestmentDetails> investmentDetailsSet;

	@OneToMany
	@JoinColumn(name = "child_id", referencedColumnName = "id")
	private HashSet<Child> childSet;

	@OneToMany
	@JoinColumn(name = "product-id", referencedColumnName = "id")
	private HashSet<Product> productSet;

	@OneToMany
	@JoinColumn(name = "transaction_id", referencedColumnName = "id")
	@Embedded
	private HashSet<Transaction> transactionSet;
}
