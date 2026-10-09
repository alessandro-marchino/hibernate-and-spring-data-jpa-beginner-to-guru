package guru.springframework.spjpaintro;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

import guru.springframework.spjpaintro.domain.Book;
import guru.springframework.spjpaintro.repositories.BookRepository;

@DataJpaTest
@ComponentScan(basePackages = "guru.springframework.spjpaintro.bootstrap")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SpringBootJpaTestSlice {

	@Autowired BookRepository bookRepository;

	@Test
	@Order(1)
	@Commit
	void testBookRepository() {
		long countBefore = bookRepository.count();
		assertThat(countBefore).isEqualTo(2);
		bookRepository.save(new Book("My Book", "1235555", "Self"));
		long countAfter = bookRepository.count();
		assertThat(countBefore).isLessThan(countAfter);
	}

	@Test
	@Order(2)
	void testBookRepositoryTransaction() {
		long countBefore = bookRepository.count();
		assertThat(countBefore).isEqualTo(3);
	}
}
