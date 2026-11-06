package guru.springframework.orderservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "order_line")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class OrderLine extends BaseEntity {
	@EqualsAndHashCode.Include
	private Integer quantityOrdered;
	@ManyToOne
	private OrderHeader orderHeader;

	@ManyToOne
	private Product product;
}
