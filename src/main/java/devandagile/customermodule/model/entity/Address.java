package devandagile.customermodule.model.entity;

import devandagile.customermodule.model.enums.States;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public final class Address {
	private Integer houseNumber;
	private String street;
	private String landmark;
	private String city;
	private States state;
	private String country;
}
