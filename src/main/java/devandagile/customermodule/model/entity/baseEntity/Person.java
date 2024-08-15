package devandagile.customermodule.model.entity.baseEntity;

import devandagile.customermodule.model.enums.Gender;
import devandagile.customermodule.model.enums.MethodFor2FA;
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
	@Column(nullable = false)
	private boolean isEnabled = false;

	@Builder.Default
	@Column(nullable = false)
	private boolean enable2FA = false;

	private MethodFor2FA methodFor2FA;

	@Column(nullable = false, updatable = false)
	private String firstName;
	@Column(nullable = false, updatable = false)
	private String lastName;
	@Column(nullable = true, updatable = false)
	private Gender gender;
	@Column(nullable = true, updatable = false)
	private LocalDateTime dob;

	@Embedded
	private Address address;

	@Column(nullable = false)
	private String passwordHash;

	@Column(nullable = false, unique = true)
	private String phone;

	@Email
	@Column(nullable = false, unique = true)
	private String email;


	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
}
