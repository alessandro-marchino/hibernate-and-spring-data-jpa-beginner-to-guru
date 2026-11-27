package guru.springframework.orderservice.domain;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Address {
	private String address;
	@Length(max = 30)
	private String city;
	private String state;
	private String zipCode;
}
