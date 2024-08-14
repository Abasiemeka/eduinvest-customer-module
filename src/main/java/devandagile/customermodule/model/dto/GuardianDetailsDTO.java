package devandagile.customermodule.model.dto;

import devandagile.customermodule.model.entity.baseEntity.Address;
import devandagile.customermodule.model.enums.CustomerType;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Builder;

@Validated
@Builder
public record GuardianDetailsDTO(
				@Column(nullable = false, updatable = false)
				String firstName,

				@Column(nullable = false, updatable = false)
				String lastName,

				@Column(nullable = false, updatable = false)
				LocalDateTime dob,

				@Column(nullable = false, unique = true)
				String phone,

				@Embedded
				@Column(nullable = false, updatable = false)
				Address address,

				@Column(nullable = false)
				CustomerType relationship
				) {
}
