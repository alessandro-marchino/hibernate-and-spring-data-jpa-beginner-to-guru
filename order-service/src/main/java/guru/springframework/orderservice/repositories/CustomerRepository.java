package guru.springframework.orderservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.orderservice.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	// Empty
    Optional<Customer> findCustomerByCustomerNameIgnoreCase(String customerName);
}
