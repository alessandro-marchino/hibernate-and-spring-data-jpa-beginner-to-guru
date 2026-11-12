package guru.springframework.orderservice.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.orderservice.domain.Customer;
import guru.springframework.orderservice.domain.OrderHeader;
import guru.springframework.orderservice.repositories.CustomerRepository;
import guru.springframework.orderservice.repositories.OrderHeaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

	private final OrderHeaderRepository orderHeaderRepository;
	private final CustomerRepository customerRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		OrderHeader orderHeader = orderHeaderRepository.findById(128L).orElseThrow();
		orderHeader.getOrderLines().forEach(ol -> {
			log.info(ol.getProduct().getDescription());
			ol.getProduct().getCategories().forEach(cat -> log.info(cat.getDescription()));
		});

		Customer customer = new Customer();
		customer.setCustomerName("Testing version");
		Customer savedCustomer = customerRepository.save(customer);
		log.info("Version is: {}", savedCustomer.getVersion());
		customerRepository.deleteById(customer.getId());

	}
}
