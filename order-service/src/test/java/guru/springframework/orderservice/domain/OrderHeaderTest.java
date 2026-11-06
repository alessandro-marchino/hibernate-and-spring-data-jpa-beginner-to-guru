package guru.springframework.orderservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class OrderHeaderTest {

	@Test
	void testEquals() {
		assertThat(OrderHeader.builder().id(1L).build())
			.isEqualTo(OrderHeader.builder().id(1L).build());
	}

	@Test
	void testEqualsIgnoreCustomerName() {
		assertThat(OrderHeader.builder().id(1L).customer(Customer.builder().customerName("Customer 1").build()).build())
			.isEqualTo(OrderHeader.builder().id(1L).customer(Customer.builder().customerName("Customer 2").build()).build());
	}

	@Test
	void testNotEquals() {
		assertThat(OrderHeader.builder().id(1L).build())
			.isNotEqualTo(OrderHeader.builder().id(3L).build());
	}
}
