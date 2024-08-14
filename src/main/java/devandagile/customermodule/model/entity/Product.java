package devandagile.customermodule.model.entity;

import devandagile.customermodule.enums.ProductType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

import java.time.LocalDate;
import java.util.HashSet;

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

	@OneToMany(mappedBy = "product")
	private HashSet<InvestmentDetails> investmentDetailsSet;
}