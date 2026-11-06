package guru.springframework.orderservice.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;

import guru.springframework.orderservice.domain.Product;
import guru.springframework.orderservice.domain.ProductStatus;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTest {
	@Autowired ProductRepository repository;

	@Test
	void testFindAll() {
		List<Product> res = repository.findAll();
		assertThat(res).hasSize(5);

		assertThat(res.stream().filter(p -> ProductStatus.NEW.equals(p.getProductStatus())).toList()).hasSize(2);
		assertThat(res.stream().filter(p -> ProductStatus.IN_STOCK.equals(p.getProductStatus())).toList()).hasSize(2);
		assertThat(res.stream().filter(p -> ProductStatus.DISCONTINUED.equals(p.getProductStatus())).toList()).hasSize(1);
	}

	@Test
	void testFindById() {
		Optional<Product> res = repository.findById(1L);
		assertThat(res).isPresent();
		assertThat(res.get().getDescription()).isEqualTo("Product 1");
	}

	@Test
	void testFindByIdNotFound() {
		Optional<Product> res = repository.findById(999L);
		assertThat(res).isEmpty();
	}

	@Test
	void testInsert() {
		Product saved = repository.save(Product.builder().description("My test product").productStatus(ProductStatus.NEW).build());
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isGreaterThan(5L);
		assertThat(saved.getDescription()).isEqualTo("My test product");
		assertThat(saved.getCreatedDate()).isNotNull();
		assertThat(saved.getLastModifiedDate()).isNotNull();
	}

	@Test
	void testUpdate() {
		Product saved = repository.save(Product.builder().description("My test product").productStatus(ProductStatus.NEW).build());
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getCreatedDate()).isNotNull();
		assertThat(saved.getLastModifiedDate()).isNotNull();

		saved.setDescription("My new name");
		Product updated = repository.saveAndFlush(saved);
		assertThat(updated.getId()).isEqualTo(saved.getId());
		assertThat(updated.getDescription()).isEqualTo("My new name");
		assertThat(updated.getCreatedDate()).isNotNull();
		assertThat(updated.getLastModifiedDate()).isNotNull();
	}

	@Test
	void testDelete() {
		Product saved = repository.save(Product.builder().description("My test product").productStatus(ProductStatus.NEW).build());
		assertThat(saved).isNotNull();

		repository.deleteById(saved.getId());

		Optional<Product> res = repository.findById(saved.getId());
		assertThat(res).isEmpty();
	}
}
