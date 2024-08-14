package devandagile.customermodule.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
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

	private String referral;
}