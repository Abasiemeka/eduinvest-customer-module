package devandagile.customermodule.model.entity;

import devandagile.customermodule.model.entity.baseEntity.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Child extends Person {

	@ManyToOne(
			targetEntity = Customer.class,
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			optional = false)
	private Customer customer;

	@OneToOne
	private InvestmentDetails investmentDetails;

}