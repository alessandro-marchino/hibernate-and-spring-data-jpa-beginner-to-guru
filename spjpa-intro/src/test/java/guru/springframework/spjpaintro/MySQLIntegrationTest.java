package guru.springframework.spjpaintro;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.spjpaintro.repositories.BookRepository;

@DataJpaTest
@ComponentScan(basePackages = "guru.springframework.spjpaintro.bootstrap")
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MySQLIntegrationTest {
	@Autowired BookRepository bookRepository;

	@Test
	void testMySQL() {
		long countBefore = bookRepository.count();
		assertThat(countBefore).isEqualTo(2);
	}
}
