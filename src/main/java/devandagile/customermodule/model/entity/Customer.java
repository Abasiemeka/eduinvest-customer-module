package devandagile.customermodule.model.entity;

import devandagile.customermodule.model.entity.baseEntity.Person;
import devandagile.customermodule.model.enums.CustomerType;
import devandagile.customermodule.model.enums.Roles;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
@Setter
@SuperBuilder
public class Customer extends Person {

	@OneToOne
	@JoinColumn(name = "account_name", referencedColumnName = "id")
	private UserAccount account;

	private String referralCode;
	private CustomerType relationship;
	private LocalDate registrationDate;

	@NotNull
	private Roles roles;

	@OneToMany
	@JoinColumn(name = "investment_details", referencedColumnName = "id")
	@Embedded
	private Set<InvestmentDetails> investmentDetailsSet;

	@OneToMany
	@JoinColumn(name = "child_id", referencedColumnName = "id")
	private Set<Child> childSet;

	@OneToMany
	@JoinColumn(name = "product-id", referencedColumnName = "id")
	private Set<Product> productSet;

	@OneToMany
	@JoinColumn(name = "transaction_id", referencedColumnName = "id")
	@Embedded
	private Set<Transaction> transactionSet;
}
