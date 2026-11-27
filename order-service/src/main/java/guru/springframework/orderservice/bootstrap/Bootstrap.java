package guru.springframework.orderservice.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.orderservice.domain.Customer;
import guru.springframework.orderservice.domain.OrderHeader;
import guru.springframework.orderservice.domain.Product;
import guru.springframework.orderservice.domain.ProductStatus;
import guru.springframework.orderservice.repositories.CustomerRepository;
import guru.springframework.orderservice.repositories.OrderHeaderRepository;
import guru.springframework.orderservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

	private final OrderHeaderRepository orderHeaderRepository;
	private final CustomerRepository customerRepository;

	private final ProductService productService;

	private void updateProduct() {
		Product product = new Product();
		product.setDescription("My Product");
		product.setProductStatus(ProductStatus.NEW);
		Product savedProduct = productService.saveProduct(product);
		Product savedProduct2 = productService.updateQuantityOnHand(savedProduct.getId(), 25);
		log.info("Updated Qty: {}", savedProduct2.getQuantityOnHand());
		productService.deleteProduct(savedProduct.getId());
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		updateProduct();

		OrderHeader orderHeader = orderHeaderRepository.findById(128L).orElseThrow();
		orderHeader.getOrderLines().forEach(ol -> {
			log.info(ol.getProduct().getDescription());
			ol.getProduct().getCategories().forEach(cat -> log.info(cat.getDescription()));
		});

		Customer customer = new Customer();
		customer.setCustomerName("Testing version");
		Customer savedCustomer = customerRepository.saveAndFlush(customer);
		log.info("Version is: {} - id is {}", savedCustomer.getVersion(), savedCustomer.getId());

		savedCustomer.setCustomerName("Testing version 2");
		customerRepository.saveAndFlush(savedCustomer);
		log.info("Version is: {} - id is {}", savedCustomer.getVersion(), savedCustomer.getId());

		savedCustomer.setCustomerName("Testing version 3");
		customerRepository.saveAndFlush(savedCustomer);
		log.info("Version is: {} - id is {}", savedCustomer.getVersion(), savedCustomer.getId());

		customerRepository.deleteById(customer.getId());
	}
}
