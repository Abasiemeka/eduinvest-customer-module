package devandagile.customermodule.model.entity;

import devandagile.customermodule.model.entity.baseEntity.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Child extends Person {
	@OneToMany(mappedBy = "child")
	private HashSet<InvestmentDetails> investmentDetailsSet;

	@ManyToOne(
			targetEntity = Customer.class,
			cascade = CascadeType.DETACH,
			fetch = FetchType.LAZY)
	private Customer customer;

	@OneToMany
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private HashSet<Product> productSet;

}