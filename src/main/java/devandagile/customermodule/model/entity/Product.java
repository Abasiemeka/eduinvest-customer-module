package devandagile.customermodule.model.entity;

import devandagile.customermodule.model.enums.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "id", nullable = false)
	private Long id;

	private Long cost;
	private ProductType productType;
	private Integer productDuration;
	private LocalDate purchaseDate;

	@ManyToOne(targetEntity = Customer.class, cascade = CascadeType.DETACH)
	private Customer customer;

	@ManyToOne(targetEntity = Child.class, cascade = CascadeType.DETACH)
	private Child child;

	@Embedded
	@OneToMany(mappedBy = "product")
	private Set<InvestmentDetails> investmentDetailsSet = new HashSet<>();

	@Embedded
	@OneToOne
	private Transaction transaction;
}