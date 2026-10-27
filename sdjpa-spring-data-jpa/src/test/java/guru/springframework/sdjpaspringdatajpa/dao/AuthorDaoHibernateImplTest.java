package guru.springframework.sdjpaspringdatajpa.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import guru.springframework.sdjpaspringdatajpa.domain.Author;
import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AuthorDaoHibernateImplTest {

	@Autowired EntityManager em;
	AuthorDao authorDao;

	@BeforeEach
	void setUp() {
		authorDao = new AuthorDaoHibernateImpl(em);
	}

	@Test
	void testFindAll() {
		List<Author> authors = authorDao.findAll();
		assertThat(authors).isNotEmpty();
	}

	@Test
	void testFindAllByLastName() {
		List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith", PageRequest.of(0, 10, Sort.by(Direction.ASC, Author::getFirstName)));
		assertThat(authors).isNotEmpty();
		assertThat(authors).hasSize(10);
		assertThat(authors.getFirst().getFirstName()).isLessThanOrEqualTo(authors.getLast().getFirstName());
	}

	@Test
	void testFindAllByLastNameWithoutSorting() {
		List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith", PageRequest.of(0, 10));
		assertThat(authors).isNotEmpty();
		assertThat(authors).hasSize(10);
	}

	@Test
	void testFindAllByLastNameSortingDesc() {
		List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith", PageRequest.of(0, 10, Sort.by(Direction.DESC, Author::getFirstName)));
		assertThat(authors).isNotEmpty();
		assertThat(authors).hasSize(10);
		assertThat(authors.getFirst().getFirstName()).isGreaterThanOrEqualTo(authors.getLast().getFirstName());
	}

	@Test
	void testFindAllByLastNameEmptyPage() {
		List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith", PageRequest.of(10, 10, Sort.by(Direction.ASC, Author::getFirstName)));
		assertThat(authors).isEmpty();
	}

	@Test
	void testFindAllByLastNameNoRecords() {
		List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smithsonian", PageRequest.of(0, 10, Sort.by(Direction.ASC, Author::getFirstName)));
		assertThat(authors).isEmpty();
	}

	@Test
	void testGetById() {
		Optional<Author> author = authorDao.getById(1L);
		assertThat(author).isNotEmpty();
		assertThat(author.get().getId()).isEqualTo(1L);
		assertThat(author.get().getFirstName()).isEqualTo("Craig");
	}

	@Test
	void testGetByIdNotFound() {
		Optional<Author> author = authorDao.getById(999L);
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
	void testListAuthorByLastNameLike() {
		List<Author> authors = authorDao.listAuthorByLastNameLike("Wall");
		assertThat(authors).isNotEmpty();
	}

	@Test
	void testGetByNameNotFound() {
		Optional<Author> author = authorDao.findAuthorByName("John", "Thompson");
		assertThat(author).isEmpty();
	}

	@Test
	void testGetByNameCriteria() {
		Optional<Author> author = authorDao.findAuthorByNameCriteria("Craig", "Walls");
		assertThat(author).isNotEmpty();
		assertThat(author.get().getId()).isEqualTo(1L);
		assertThat(author.get().getFirstName()).isEqualTo("Craig");
		assertThat(author.get().getLastName()).isEqualTo("Walls");
	}

	@Test
	void testGetByNameNative() {
		Optional<Author> author = authorDao.findAuthorByNameNative("Craig", "Walls");
		assertThat(author).isNotEmpty();
		assertThat(author.get().getId()).isEqualTo(1L);
		assertThat(author.get().getFirstName()).isEqualTo("Craig");
		assertThat(author.get().getLastName()).isEqualTo("Walls");
	}

	@Test
	void testSave() {
		Author author = authorDao.saveNewAuthor(Author.builder().firstName("John").lastName("Thompson").build());
		assertThat(author).isNotNull();
		assertThat(author.getId()).isGreaterThan(0L);
	}

	@Test
	void testUpdate() {
		Author author = Author.builder().firstName("John").lastName("T").build();
		Author saved = authorDao.saveNewAuthor(author);
		saved.setLastName("Thompson");
		Author updated = authorDao.updateAuthor(saved);
		assertThat(updated).isNotNull();
		assertThat(updated.getId()).isGreaterThan(0L);
		assertThat(updated.getLastName()).isEqualTo("Thompson");
	}

	@Test
	void testDelete() {
		Author saved = authorDao.saveNewAuthor(Author.builder().firstName("John").lastName("T").build());
		authorDao.deleteAuthorById(saved.getId());

		Optional<Author> found = authorDao.getById(saved.getId());
		assertThat(found).isEmpty();
	}

	@Test
	void testDeleteNotExisting() {
		// Assert not throws
		authorDao.deleteAuthorById(999L);
	}
}


