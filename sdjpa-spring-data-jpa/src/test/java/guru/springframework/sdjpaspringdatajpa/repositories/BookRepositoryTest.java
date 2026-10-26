package guru.springframework.sdjpaspringdatajpa.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.dao.EmptyResultDataAccessException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookRepositoryTest {
	@Autowired BookRepository bookRepository;
	@Test
	void testEmptyResultException() {
		assertThrows(EmptyResultDataAccessException.class, () -> bookRepository.readByTitle("Foobar4"));
	}

	@Test
	void testNullParam() {
		assertNull(bookRepository.getByTitle(null));
	}

	@Test
	void testNoException() {
		assertNull(bookRepository.getByTitle("Foobar4"));
	}

	@Test
	void testBookStream() {
		AtomicInteger count = new AtomicInteger();
		bookRepository.findAllByTitleNotNull().forEach(_ -> count.incrementAndGet());
		assertThat(count.get()).isEqualTo(5);
	}
}
