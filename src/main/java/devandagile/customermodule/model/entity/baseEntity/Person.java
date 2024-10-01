package devandagile.customermodule.model.entity.baseEntity;

import devandagile.customermodule.model.entity.Address;
import devandagile.customermodule.model.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

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
	private String password;

	@Column(nullable = false, unique = true)
	private String phone;

	@NonNull
	@Column(nullable = false, unique = true)
	private String email;


	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
}
