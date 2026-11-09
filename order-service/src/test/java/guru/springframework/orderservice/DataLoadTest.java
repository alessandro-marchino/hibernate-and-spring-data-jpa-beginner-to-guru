package guru.springframework.orderservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Commit;

import guru.springframework.orderservice.domain.Address;
import guru.springframework.orderservice.domain.Customer;
import guru.springframework.orderservice.domain.OrderHeader;
import guru.springframework.orderservice.domain.OrderLine;
import guru.springframework.orderservice.domain.Product;
import guru.springframework.orderservice.domain.ProductStatus;
import guru.springframework.orderservice.repositories.CustomerRepository;
import guru.springframework.orderservice.repositories.OrderHeaderRepository;
import guru.springframework.orderservice.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
public class DataLoadTest {

	final String PRODUCT_D1 = "Product 1";
	final String PRODUCT_D2 = "Product 2";
	final String PRODUCT_D3 = "Product 3";

	final String TEST_CUSTOMER = "TEST CUSTOMER";

	@Autowired OrderHeaderRepository orderHeaderRepository;
	@Autowired CustomerRepository customerRepository;
	@Autowired ProductRepository productRepository;

	@Test
	@Commit
	@Disabled
	void testDataLoader() {
		List<Product> products = loadProducts();
		Customer customer = loadCustomer();
		int ordersToCreate = 100;

		for(int i = 0; i < ordersToCreate; i++) {
			log.info("Creating order #: {}", i + 1);
			saveOrder(customer, products);
		}
		orderHeaderRepository.flush();
	}

	private OrderHeader saveOrder(Customer customer, List<Product> products) {
		Random random = new Random();

		OrderHeader orderHeader = new OrderHeader();
		orderHeader.setCustomer(customer);
		products.forEach(product -> {
			OrderLine orderLine = new OrderLine();
			orderLine.setProduct(product);
			orderLine.setQuantityOrdered(random.nextInt(20));
			orderHeader.addOrderLine(orderLine);
		});
		return orderHeaderRepository.save(orderHeader);
	}

	private Customer loadCustomer() {
		return getOrSaveCustomer(TEST_CUSTOMER);
	}

	private Customer getOrSaveCustomer(String customerName) {
		return customerRepository.findCustomerByCustomerNameIgnoreCase(customerName)
			.orElseGet(() -> {
				Customer c = new Customer();
				c.setCustomerName(customerName);
				c.setEmail("test@example.com");
				c.setAddress(new Address());
				c.getAddress().setAddress("123 Main");
				c.getAddress().setCity("New Orleans");
				c.getAddress().setState("LA");
				return customerRepository.save(c);
			});
	}

	private List<Product> loadProducts() {
		List<Product> products = new ArrayList<>();

		products.add(getOrSaveProduct(PRODUCT_D1));
		products.add(getOrSaveProduct(PRODUCT_D2));
		products.add(getOrSaveProduct(PRODUCT_D3));

		return products;
	}

	private Product getOrSaveProduct(String description) {
		return productRepository.findByDescription(description)
			.orElseGet(() -> {
				Product p = new Product();
				p.setDescription(description);
				p.setProductStatus(ProductStatus.NEW);
				return productRepository.save(p);
			});
	}
}
