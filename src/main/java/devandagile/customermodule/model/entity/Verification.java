package devandagile.customermodule.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class Verification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@NotBlank
	@NotNull
	@Column(unique = true, nullable = false, updatable = false)
	@Email
	private String email;

	@Column(nullable = false, unique = true)
	private String token;
	private LocalDateTime expiresAt;
	private Integer validityDuration;

	public Verification(String email, Integer validityDuration) {
		this.email = email;
		this.validityDuration = validityDuration;
		this.token = UUID.randomUUID().toString();
	}

	@PostLoad
	@PostPersist
	private void calculateExpiry() {
		this.expiresAt = createdAt.plusMinutes(validityDuration);
	}

	public boolean isExpired() {
		return LocalDateTime.now().isAfter(this.expiresAt);
	}
}
