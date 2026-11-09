package guru.springframework.orderservice.domain;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

	private Address shippingAddress;
	private Address billToAddress;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	@OneToMany(mappedBy = "orderHeader", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	@ToString.Exclude
	@Builder.Default
	private Set<OrderLine> orderLines = new HashSet<>();
	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
	private OrderApproval orderApproval;

	public void addOrderLine(OrderLine orderLine) {
		orderLines.add(orderLine);
		orderLine.setOrderHeader(this);
	}

	public void setOrderApproval(OrderApproval orderApproval) {
		this.orderApproval = orderApproval;
		orderApproval.setOrderHeader(this);
	}
}
