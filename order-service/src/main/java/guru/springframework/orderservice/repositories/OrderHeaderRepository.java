package guru.springframework.orderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.orderservice.domain.OrderHeader;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
	// Empty
}
