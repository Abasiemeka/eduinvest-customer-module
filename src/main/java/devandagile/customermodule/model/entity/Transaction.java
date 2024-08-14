package devandagile.customermodule.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@NotNull
	private Long amount;

	@OneToOne
	private Customer customer;

	@OneToOne
	private Child child;

	@OneToOne
	private Product product;

	@OneToOne
	private String referral;
}