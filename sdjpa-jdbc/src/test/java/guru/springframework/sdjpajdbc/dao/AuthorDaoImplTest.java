package guru.springframework.sdjpajdbc.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.ComponentScan;

import guru.springframework.sdjpajdbc.domain.Author;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ComponentScan(basePackages = { "guru.springframework.sdjpajdbc.dao" })
public class AuthorDaoImplTest {

	@Autowired AuthorDao authorDao;

	@Test
	void testGetById() {
		Optional<Author> author = authorDao.getById(1L);
		assertThat(author).isNotEmpty();
	}
}
