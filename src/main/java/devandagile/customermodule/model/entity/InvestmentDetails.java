package devandagile.customermodule.model.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Embeddable
public class InvestmentDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne(targetEntity = Customer.class, cascade = CascadeType.DETACH)
	private Customer customer;

	@ManyToOne(targetEntity = Child.class, cascade = CascadeType.DETACH)
	private Child child;

	@ManyToOne(targetEntity = Product.class, cascade = CascadeType.DETACH)
	private Product product;
}