package guru.springframework.spjpaintro;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import guru.springframework.spjpaintro.domain.Book;
import guru.springframework.spjpaintro.repositories.BookRepository;

@DataJpaTest
public class SpringBootJpaTestSlice {

	@Autowired BookRepository bookRepository;

	@Test
	void testBookRepository() {
		long countBefore = bookRepository.count();
		bookRepository.save(new Book("My Book", "1235555", "Self"));
		long countAfter = bookRepository.count();
		assertThat(countBefore).isLessThan(countAfter);
	}
}
