package devandagile.customermodule.model.entity;

import devandagile.customermodule.model.entity.baseEntity.Person;
import devandagile.customermodule.model.enums.CustomerType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
	private Set<InvestmentDetails> investmentDetailsSet = new HashSet<>();

	@OneToMany
	@JoinColumn(name = "child_id", referencedColumnName = "id")
	private Set<Child> childSet = new HashSet<>();

	@OneToMany
	@JoinColumn(name = "product-id", referencedColumnName = "id")
	private Set<Product> productSet = new HashSet<>();

	@OneToMany
	@JoinColumn(name = "transaction_id", referencedColumnName = "id")
	@Embedded
	private Set<Transaction> transactionSet = new HashSet<>();
}
