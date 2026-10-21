package guru.springframework.sdjpajdbc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;

import guru.springframework.sdjpajdbc.repository.AuthorRepository;
import guru.springframework.sdjpajdbc.repository.BookRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SdjpaIntegrationTest {

	@Autowired BookRepository bookRepository;
	@Autowired AuthorRepository authorRepository;

	@Test
	void testMySQL() {
		long countBook = bookRepository.count();
		assertThat(countBook).isEqualTo(5L);

		long countAuthor = authorRepository.count();
		assertThat(countAuthor).isEqualTo(2L);
	}
}
