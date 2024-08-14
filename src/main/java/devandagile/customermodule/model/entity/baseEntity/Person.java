package devandagile.customermodule.model.entity.baseEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
@ToString
@Getter
@Setter
public abstract class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Builder.Default
	@Column(nullable = false, updatable = true)
	private boolean isEnabled = false;

	@Builder.Default
	@Column(nullable = false, updatable = true)
	private boolean google2FAEnabled = false;

	@Column(nullable = false, updatable = false)
	private String firstName;
	@Column(nullable = false, updatable = false)
	private String lastName;
	@Column(nullable = false, updatable = false)
	private LocalDateTime dob;
	@Email
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false, unique = true)
	private String phone;
	@Column(nullable = false)
	private String passwordHash;

	@Embedded
	private Address address;

	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
}
