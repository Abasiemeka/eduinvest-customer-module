package devandagile.customermodule.model.entity;

import devandagile.customermodule.model.entity.baseEntity.Person;
import devandagile.customermodule.model.enums.TransactionStatus;
import devandagile.customermodule.model.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.STRING)
	private TransactionType type;

	@CreationTimestamp
	private LocalDateTime initiatedAt;

	private LocalDateTime completedAt;

	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

	@NotNull
	private Long amount;

	private Long senderId;

	private Long receiverId;

	private Class<? extends Person> beneficiary;

	private String narration;
}