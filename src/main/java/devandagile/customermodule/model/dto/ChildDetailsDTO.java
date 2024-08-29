package devandagile.customermodule.model.dto;

import devandagile.customermodule.model.entity.baseEntity.Address;
import devandagile.customermodule.model.enums.Gender;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Builder
@Validated
public record ChildDetailsDTO(
				@Column(nullable = false, updatable = false)
				String firstName,
				@Column(nullable = false, updatable = false)
				String lastName,
				@Column(nullable = false, updatable = false)
				Gender gender,
				@Column(nullable = false, updatable = false)
				LocalDateTime dob,

				@Embedded
				@AttributeOverride(name = "houseNumber", column = @Column(name = "number"))
				@AttributeOverride(name = "street", column = @Column(name = "street/road"))
				@AttributeOverride(name = "landmark", column = @Column(name = "zip"))
				Address SchoolAddress
				) {
}
