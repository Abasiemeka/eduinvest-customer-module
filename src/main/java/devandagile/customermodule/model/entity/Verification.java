package devandagile.customermodule.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@Entity
public class Verification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	private LocalDateTime createdAt;

	private final String email;
	private final LocalDateTime expiresAt;
	private final Integer validityDuration;
	private final String token;

	public Verification(String email, Integer validityDuration) {
		this.email = email;
		this.validityDuration = validityDuration;
		this.createdAt = LocalDateTime.now();
		this.expiresAt = createdAt.plusMinutes(validityDuration);
		this.token = UUID.randomUUID().toString();
	}

	private boolean isExpired() {
		return LocalDateTime.now().isAfter(this.expiresAt);
	}
}
