package guru.springframework.orderservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.orderservice.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	// Empty
}
