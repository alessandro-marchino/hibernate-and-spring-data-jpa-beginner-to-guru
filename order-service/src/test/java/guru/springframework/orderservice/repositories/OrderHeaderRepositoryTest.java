package guru.springframework.orderservice.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;

import guru.springframework.orderservice.domain.Customer;
import guru.springframework.orderservice.domain.OrderApproval;
import guru.springframework.orderservice.domain.OrderHeader;
import guru.springframework.orderservice.domain.OrderLine;
import guru.springframework.orderservice.domain.OrderStatus;
import guru.springframework.orderservice.domain.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderHeaderRepositoryTest {
	@Autowired OrderHeaderRepository repository;
	@Autowired ProductRepository productRepository;
	@Autowired CustomerRepository customerRepository;
	@Autowired OrderApprovalRepository orderApprovalRepository;

	@Test
	void testFindAll() {
		List<OrderHeader> res = repository.findAll();
		assertThat(res).hasSize(5);
	}

	@Test
	void testFindById() {
		Optional<OrderHeader> res = repository.findById(1L);
		assertThat(res).isPresent();
		assertThat(res.get().getCustomer().getCustomerName()).isEqualTo("Customer 1");
	}

	@Test
	void testFindByIdNotFound() {
		Optional<OrderHeader> res = repository.findById(999L);
		assertThat(res).isEmpty();
	}

	@Test
	void testInsert() {
		Customer customer = createCustomer();
		OrderHeader saved = repository.save(OrderHeader.builder().customer(customer).orderStatus(OrderStatus.NEW).build());
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isGreaterThan(5L);
		assertThat(saved.getCustomer().getCustomerName()).isEqualTo("My test customer");
		assertThat(saved.getCreatedDate()).isNotNull();
		assertThat(saved.getLastModifiedDate()).isNotNull();
	}

	@Test
	void testInsertWithLine() {
		Product product = createProduct();
		Customer customer = createCustomer();
		OrderApproval approval = createApproval();

		OrderHeader oh = OrderHeader.builder()
			.customer(customer)
			.orderStatus(OrderStatus.NEW)
			.orderApproval(approval)
			.build();
		oh.addOrderLine(OrderLine.builder()
			.product(product)
			.quantityOrdered(5)
			.build());
		OrderHeader saved = repository.save(oh);
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isGreaterThan(5L);
		assertThat(saved.getCustomer().getCustomerName()).isEqualTo("My test customer");
		assertThat(saved.getCreatedDate()).isNotNull();
		assertThat(saved.getLastModifiedDate()).isNotNull();

		assertThat(saved.getOrderLines()).hasSize(1);
		assertThat(saved.getOrderApproval().getId()).isEqualTo(approval.getId());
	}

	@Test
	void testUpdate() {
		Customer customer = createCustomer();
		OrderHeader saved = repository.saveAndFlush(OrderHeader.builder().customer(customer).build());
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getCreatedDate()).isNotNull();
		assertThat(saved.getLastModifiedDate()).isNotNull();

		Customer newCustomer = customerRepository.saveAndFlush(Customer.builder().customerName("My new customer").build());
		saved.setCustomer(newCustomer);
		OrderHeader updated = repository.saveAndFlush(saved);
		assertThat(updated.getId()).isEqualTo(saved.getId());
		assertThat(updated.getCustomer().getCustomerName()).isEqualTo("My new customer");
		assertThat(updated.getCreatedDate()).isNotNull();
		assertThat(updated.getLastModifiedDate()).isNotNull();
	}

	@Test
	void testDelete() {
		Customer customer = createCustomer();
		OrderHeader saved = repository.save(OrderHeader.builder().customer(customer).build());
		assertThat(saved).isNotNull();

		repository.deleteById(saved.getId());

		Optional<OrderHeader> res = repository.findById(saved.getId());
		assertThat(res).isEmpty();

		Optional<Customer> customerSearch = customerRepository.findById(customer.getId());
		assertThat(customerSearch).isNotEmpty();
	}

	private Product createProduct() {
		return productRepository.saveAndFlush(Product.builder().description("Test Product").build());
	}

	private Customer createCustomer() {
		return customerRepository.saveAndFlush(Customer.builder().customerName("My test customer").build());
	}

	private OrderApproval createApproval() {
		return orderApprovalRepository.saveAndFlush(OrderApproval.builder().approvedBy("Me").build());
	}
}
