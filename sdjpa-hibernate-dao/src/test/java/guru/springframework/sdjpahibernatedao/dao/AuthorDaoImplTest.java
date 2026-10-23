package guru.springframework.sdjpahibernatedao.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.ComponentScan;

import guru.springframework.sdjpahibernatedao.domain.Author;
import jakarta.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ComponentScan(basePackages = { "guru.springframework.sdjpajdbc.dao" })
public class AuthorDaoImplTest {

	@Autowired AuthorDao authorDao;

	@Test
	void testGetById() {
		Optional<Author> author = authorDao.getById(1L);
		assertThat(author).isNotEmpty();
		assertThat(author.get().getId()).isEqualTo(1L);
		assertThat(author.get().getFirstName()).isEqualTo("Craig");
	}

	@Test
	void testGetByIdNotFound() {
		Optional<Author> author = authorDao.getById(100L);
		assertThat(author).isEmpty();
	}

	@Test
	void testGetByName() {
		Optional<Author> author = authorDao.findAuthorByName("Craig", "Walls");
		assertThat(author).isNotEmpty();
		assertThat(author.get().getId()).isEqualTo(1L);
		assertThat(author.get().getFirstName()).isEqualTo("Craig");
		assertThat(author.get().getLastName()).isEqualTo("Walls");
	}

	@Test
	void testGetByNameNotFound() {
		Optional<Author> author = authorDao.findAuthorByName("John", "Thompson");
		assertThat(author).isEmpty();
	}

	@Test
	@Transactional
	void testSave() {
		Author author = authorDao.saveNewAuthor(Author.builder().firstName("John").lastName("Thompson").build());
		assertThat(author).isNotNull();
		assertThat(author.getId()).isGreaterThan(0L);
	}

	@Test
	@Transactional
	void testUpdate() {
		Author saved = authorDao.saveNewAuthor(Author.builder().firstName("John").lastName("T").build());
		saved.setLastName("Thompson");
		Author updated = authorDao.updateAuthor(saved);
		assertThat(updated).isNotNull();
		assertThat(updated.getId()).isGreaterThan(0L);
		assertThat(updated.getLastName()).isEqualTo("Thompson");
	}

	@Test
	@Transactional
	void testDelete() {
		Author saved = authorDao.saveNewAuthor(Author.builder().firstName("John").lastName("T").build());
		authorDao.deleteAuthorById(saved.getId());

		Optional<Author> found = authorDao.getById(saved.getId());
		assertThat(found).isEmpty();
	}

	@Test
	@Transactional
	void testDeleteNotExisting() {
		// Assert not throws
		authorDao.deleteAuthorById(999L);
	}
}
