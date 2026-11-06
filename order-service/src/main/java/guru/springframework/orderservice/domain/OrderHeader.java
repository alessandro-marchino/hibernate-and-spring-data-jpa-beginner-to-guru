package guru.springframework.orderservice.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "order_header")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)

@AttributeOverride(name = "shippingAddress.address", column = @Column(name = "shipping_address"))
@AttributeOverride(name = "shippingAddress.city", column = @Column(name = "shipping_city"))
@AttributeOverride(name = "shippingAddress.state", column = @Column(name = "shipping_state"))
@AttributeOverride(name = "shippingAddress.zipCode", column = @Column(name = "shipping_zip_code"))
@AttributeOverride(name = "billToAddress.address", column = @Column(name = "bill_to_address"))
@AttributeOverride(name = "billToAddress.city", column = @Column(name = "bill_to_city"))
@AttributeOverride(name = "billToAddress.state", column = @Column(name = "bill_to_state"))
@AttributeOverride(name = "billToAddress.zipCode", column = @Column(name = "bill_to_zip_code"))
public class OrderHeader extends BaseEntity {

	private String customerName;
	private Address shippingAddress;
	private Address billToAddress;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
}
