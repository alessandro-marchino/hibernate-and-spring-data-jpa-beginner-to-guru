package guru.springframework.spjpaintro;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.spjpaintro.repositories.BookRepository;

@SpringBootTest
class SpjpaIntroApplicationTests {
	@Autowired BookRepository bookRepository;

	@Test
	void testBookRepository() {
		long count = bookRepository.count();
		assertThat(count).isGreaterThan(0);
	}

	@Test
	void contextLoads() {
	}

}
