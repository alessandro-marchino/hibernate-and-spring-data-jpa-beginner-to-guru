package guru.springframework.orderservice.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.ComponentScan;

import guru.springframework.orderservice.domain.Product;
import guru.springframework.orderservice.domain.ProductStatus;
import guru.springframework.orderservice.services.ProductService;

@DataJpaTest
@ComponentScan(basePackageClasses = { ProductService.class })
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTest {
	@Autowired ProductRepository repository;
	@Autowired ProductService productService;

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
		assertThat(res.get().getQuantityOnHand()).isEqualTo(10);
	}

	@Test
	void testFindByIdNotFound() {
		Optional<Product> res = repository.findById(999L);
		assertThat(res).isEmpty();
	}

	@Test
	void testInsert() {
		Product saved = repository.save(Product.builder().description("My test product").productStatus(ProductStatus.NEW).quantityOnHand(15).build());
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isGreaterThan(5L);
		assertThat(saved.getQuantityOnHand()).isEqualTo(15);
		assertThat(saved.getDescription()).isEqualTo("My test product");
		assertThat(saved.getCreatedDate()).isNotNull();
		assertThat(saved.getLastModifiedDate()).isNotNull();
	}

	@Test
	void testUpdate() {
		Product saved = repository.save(Product.builder().description("My test product").productStatus(ProductStatus.NEW).quantityOnHand(15).build());
		assertThat(saved).isNotNull();
		assertThat(saved.getId()).isNotNull();
		assertThat(saved.getQuantityOnHand()).isEqualTo(15);
		assertThat(saved.getCreatedDate()).isNotNull();
		assertThat(saved.getLastModifiedDate()).isNotNull();

		saved.setDescription("My new name");
		saved.setQuantityOnHand(14);
		Product updated = repository.saveAndFlush(saved);
		assertThat(updated.getId()).isEqualTo(saved.getId());
		assertThat(updated.getDescription()).isEqualTo("My new name");
		assertThat(updated.getQuantityOnHand()).isEqualTo(14);
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

	@Test
	void testGetCategory() {
		Optional<Product> product = repository.findByDescription("Product 1");
		assertThat(product).isPresent();
		assertThat(product.get().getCategories()).hasSize(1);
		assertThat(product.get().getCategories()).first().extracting("description").isEqualTo("CAT3");
	}

	@Test
	void testGetCategoryNotFound() {
		Optional<Product> product = repository.findByDescription("Product 5");
		assertThat(product).isPresent();
		assertThat(product.get().getCategories()).isEmpty();
	}

	@Test
	void addAndUpdateProduct() {
		Product product = Product.builder().description("My Product").productStatus(ProductStatus.NEW).build();
		Product savedProduct = productService.saveProduct(product);

		Product savedProduct2 = productService.updateQuantityOnHand(savedProduct.getId(), 25);
		assertThat(savedProduct2.getId()).isEqualTo(savedProduct.getId());
		assertThat(savedProduct2.getDescription()).isEqualTo(savedProduct.getDescription());
		assertThat(savedProduct2.getQuantityOnHand()).isEqualTo(25);
	}
}
