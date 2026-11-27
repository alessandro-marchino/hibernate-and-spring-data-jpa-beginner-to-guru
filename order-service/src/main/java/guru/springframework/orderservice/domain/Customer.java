package guru.springframework.orderservice.domain;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "customer")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Customer extends BaseEntity {

	@Length(max = 50)
	private String customerName;
	@Valid
	private Address address;
	@Length(max = 20)
	private String phone;
	private String email;
	@Version
	private Integer version;

	@OneToMany(mappedBy = "customer")
	@ToString.Exclude
	private Set<OrderHeader> orderHeaders;

	public void addOrderHeader(OrderHeader orderHeader) {
		if(orderHeaders == null) {
			orderHeaders = new HashSet<>();
		}
		orderHeaders.add(orderHeader);
		orderHeader.setCustomer(this);
	}
}
