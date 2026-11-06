package guru.springframework.orderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.orderservice.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	// Empty
}
