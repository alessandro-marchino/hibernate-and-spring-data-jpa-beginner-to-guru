package guru.springframework.orderservice.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;

import guru.springframework.orderservice.domain.OrderHeader;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderHeaderRepositoryTest {
	@Autowired OrderHeaderRepository repository;

	@Test
	void testFindAll() {
		List<OrderHeader> res = repository.findAll();
		assertThat(res).hasSize(5);
	}

	@Test
	void testFindById() {
		Optional<OrderHeader> res = repository.findById(1L);
		assertThat(res).isPresent();
		assertThat(res.get().getCustomerName()).isEqualTo("Customer 1");
	}

	@Test
	void testFindByIdNotFound() {
		Optional<OrderHeader> res = repository.findById(999L);
		assertThat(res).isEmpty();
	}

	@Test
	void testInsert() {
		OrderHeader saved = repository.save(OrderHeader.builder().customerName("My test customer").build());
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isGreaterThan(5L);
		assertThat(saved.getCustomerName()).isEqualTo("My test customer");
	}

	@Test
	void testUpdate() {
		OrderHeader saved = repository.save(OrderHeader.builder().customerName("My test customer").build());
		assertThat(saved).isNotNull();

		saved.setCustomerName("My new name");
		OrderHeader updated = repository.save(saved);
		assertThat(updated.getId()).isEqualTo(saved.getId());
		assertThat(updated.getCustomerName()).isEqualTo("My new name");
	}

	@Test
	void testDelete() {
		OrderHeader saved = repository.save(OrderHeader.builder().customerName("My test customer").build());
		assertThat(saved).isNotNull();

		repository.deleteById(saved.getId());

		Optional<OrderHeader> res = repository.findById(saved.getId());
		assertThat(res).isEmpty();
	}
}
