package guru.springframework.orderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.orderservice.domain.OrderApproval;

public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {
	// Empty
}
