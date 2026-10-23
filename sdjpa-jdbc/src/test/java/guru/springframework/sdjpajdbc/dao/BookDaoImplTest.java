package guru.springframework.sdjpajdbc.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.sdjpajdbc.domain.Author;
import guru.springframework.sdjpajdbc.domain.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ComponentScan(basePackages = { "guru.springframework.sdjpajdbc.dao" })
public class BookDaoImplTest {

	@Autowired BookDao bookDao;

	@Test
	void testGetById() {
		Optional<Book> book = bookDao.getById(1L);
		assertThat(book).isNotEmpty();
		assertThat(book.get().getId()).isEqualTo(1L);
		assertThat(book.get().getTitle()).isEqualTo("Spring in Action, 5th Edition");
	}

	@Test
	void testGetByIdNotFound() {
		Optional<Book> book = bookDao.getById(100L);
		assertThat(book).isEmpty();
	}

	@Test
	void testGetByTitle() {
		Optional<Book> book = bookDao.getByTitle("Domain-Driven Design");
		assertThat(book).isNotEmpty();
		assertThat(book.get().getId()).isEqualTo(4L);
		assertThat(book.get().getTitle()).isEqualTo("Domain-Driven Design");
		assertThat(book.get().getPublisher()).isEqualTo("Addison Wesley");
		assertThat(book.get().getIsbn()).isEqualTo("978-0321125217");
		assertThat(book.get().getAuthor().getId()).isEqualTo(2L);
	}

	@Test
	void testGetByTitleNotFound() {
		Optional<Book> book = bookDao.getByTitle("My book");
		assertThat(book).isEmpty();
	}

	@Test
	@Transactional
	void testSave() {
		Book book = bookDao.save(Book.builder().title("My test Book").author(Author.builder().id(1L).build()).isbn("000-123456789").publisher("My own").build());
		assertThat(book).isNotNull();
		assertThat(book.getId()).isGreaterThan(0L);
	}

	@Test
	@Transactional
	void testSaveNullAuthor() {
		Book book = bookDao.save(Book.builder().title("My test Book").isbn("000-123456789").publisher("My own").build());
		assertThat(book).isNotNull();
		assertThat(book.getId()).isGreaterThan(0L);
		assertThat(book.getAuthor()).isNull();
	}

	@Test
	@Transactional
	void testSaveNullAuthorId() {
		Book book = bookDao.save(Book.builder().title("My test Book").author(Author.builder().build()).isbn("000-123456789").publisher("My own").build());
		assertThat(book).isNotNull();
		assertThat(book.getId()).isGreaterThan(0L);
		assertThat(book.getAuthor()).isNull();
	}

	@Test
	@Transactional
	void testUpdate() {
		Book saved = bookDao.save(Book.builder().title("My test Book").author(Author.builder().id(1L).build()).isbn("000-123456789").publisher("My own").build());
		saved.setTitle("My updated book");
		Book updated = bookDao.update(saved);
		assertThat(updated).isNotNull();
		assertThat(updated.getId()).isEqualTo(saved.getId());
		assertThat(updated.getTitle()).isEqualTo("My updated book");
	}

	@Test
	@Transactional
	void testUpdateNullAuthor() {
		Book saved = bookDao.save(Book.builder().title("My test Book").author(Author.builder().id(1L).build()).isbn("000-123456789").publisher("My own").build());
		saved.setAuthor(null);
		Book updated = bookDao.update(saved);
		assertThat(updated).isNotNull();
		assertThat(updated.getId()).isEqualTo(saved.getId());
		assertThat(updated.getAuthor()).isNull();
	}

	@Test
	@Transactional
	void testUpdateNullAuthorId() {
		Book saved = bookDao.save(Book.builder().title("My test Book").author(Author.builder().id(1L).build()).isbn("000-123456789").publisher("My own").build());
		saved.getAuthor().setId(null);
		Book updated = bookDao.update(saved);
		assertThat(updated).isNotNull();
		assertThat(updated.getId()).isEqualTo(saved.getId());
		assertThat(updated.getAuthor()).isNull();
	}

	@Test
	@Transactional
	void testDelete() {
		Book saved = bookDao.save(Book.builder().title("My test Book").author(Author.builder().id(1L).build()).isbn("000-123456789").publisher("My own").build());
		bookDao.deleteById(saved.getId());

		Optional<Book> found = bookDao.getById(saved.getId());
		assertThat(found).isEmpty();
	}
}
