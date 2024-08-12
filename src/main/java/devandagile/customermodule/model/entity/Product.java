package devandagile.customermodule.model.entity;

import devandagile.customermodule.enums.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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

	@OneToOne
	@JoinColumn(name = "customer_id", nullable = false, updatable = false, referencedColumnName = "id")
	private Customer customer;

	@OneToOne
	@JoinColumn(name = "child_id", nullable = false, updatable = false, referencedColumnName = "id")
	private Child child;
}