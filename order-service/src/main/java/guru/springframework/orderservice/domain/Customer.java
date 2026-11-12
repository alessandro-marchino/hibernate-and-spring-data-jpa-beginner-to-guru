package guru.springframework.orderservice.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
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

	private String customerName;
	private Address address;
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
