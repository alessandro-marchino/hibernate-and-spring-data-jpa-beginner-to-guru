package guru.springframework.sdjpaspringdatajpa.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.Import;

import guru.springframework.sdjpaspringdatajpa.domain.Book;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(BookDaoImpl.class)
public class BookDaoImplTest {

	@Autowired BookDao bookDao;

	@Test
	void testFindAll() {
		List<Book> books = bookDao.findAll();
		assertThat(books).isNotEmpty();
		assertThat(books).hasSize(5);
	}

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
		assertThat(book.get().getAuthorId()).isEqualTo(2L);
	}

	@Test
	void testGetByTitleCriteria() {
		Optional<Book> book = bookDao.getByTitleCriteria("Domain-Driven Design");
		assertThat(book).isNotEmpty();
		assertThat(book.get().getId()).isEqualTo(4L);
		assertThat(book.get().getTitle()).isEqualTo("Domain-Driven Design");
		assertThat(book.get().getPublisher()).isEqualTo("Addison Wesley");
		assertThat(book.get().getIsbn()).isEqualTo("978-0321125217");
		assertThat(book.get().getAuthorId()).isEqualTo(2L);
	}

	@Test
	void testGetByTitleNative() {
		Optional<Book> book = bookDao.getByTitleNative("Domain-Driven Design");
		assertThat(book).isNotEmpty();
		assertThat(book.get().getId()).isEqualTo(4L);
		assertThat(book.get().getTitle()).isEqualTo("Domain-Driven Design");
		assertThat(book.get().getPublisher()).isEqualTo("Addison Wesley");
		assertThat(book.get().getIsbn()).isEqualTo("978-0321125217");
		assertThat(book.get().getAuthorId()).isEqualTo(2L);
	}

	@Test
	void testGetByTitleNotFound() {
		Optional<Book> book = bookDao.getByTitle("My book");
		assertThat(book).isEmpty();
	}

	@Test
	void testFindByISBN() {
		Optional<Book> book = bookDao.findByISBN("978-0321125217");
		assertThat(book).isNotEmpty();
		assertThat(book.get().getId()).isEqualTo(4L);
		assertThat(book.get().getTitle()).isEqualTo("Domain-Driven Design");
		assertThat(book.get().getPublisher()).isEqualTo("Addison Wesley");
		assertThat(book.get().getIsbn()).isEqualTo("978-0321125217");
		assertThat(book.get().getAuthorId()).isEqualTo(2L);
	}

	@Test
	void testSave() {
		Book book = bookDao.save(Book.builder().title("My test Book").authorId(1L).isbn("000-123456789").publisher("My own").build());
		assertThat(book).isNotNull();
		assertThat(book.getId()).isGreaterThan(0L);
	}

	@Test
	void testSaveNullAuthor() {
		Book book = bookDao.save(Book.builder().title("My test Book").isbn("000-123456789").publisher("My own").build());
		assertThat(book).isNotNull();
		assertThat(book.getId()).isGreaterThan(0L);
		assertThat(book.getAuthorId()).isNull();
	}

	@Test
	void testUpdate() {
		Book saved = bookDao.save(Book.builder().title("My test Book").authorId(1L).isbn("000-123456789").publisher("My own").build());
		saved.setTitle("My updated book");
		Book updated = bookDao.update(saved);
		assertThat(updated).isNotNull();
		assertThat(updated.getId()).isEqualTo(saved.getId());
		assertThat(updated.getTitle()).isEqualTo("My updated book");
	}

	@Test
	void testUpdateNullAuthor() {
		Book saved = bookDao.save(Book.builder().title("My test Book").authorId(1L).isbn("000-123456789").publisher("My own").build());
		saved.setAuthorId(null);
		Book updated = bookDao.update(saved);
		assertThat(updated).isNotNull();
		assertThat(updated.getId()).isEqualTo(saved.getId());
		assertThat(updated.getAuthorId()).isNull();
	}

	@Test
	void testDelete() {
		Book saved = bookDao.save(Book.builder().title("My test Book").authorId(1L).isbn("000-123456789").publisher("My own").build());
		bookDao.deleteById(saved.getId());

		Optional<Book> found = bookDao.getById(saved.getId());
		assertThat(found).isEmpty();
	}

	@Test
	void testDeleteNotExisting() {
		// Assert not throws
		bookDao.deleteById(999L);
	}
}
