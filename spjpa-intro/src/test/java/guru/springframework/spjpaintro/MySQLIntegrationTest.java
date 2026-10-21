package guru.springframework.spjpaintro;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import guru.springframework.spjpaintro.domain.AuthorUuid;
import guru.springframework.spjpaintro.domain.BookNatural;
import guru.springframework.spjpaintro.domain.BookUuid;
import guru.springframework.spjpaintro.repositories.AuthorUuidRepository;
import guru.springframework.spjpaintro.repositories.BookNaturalRepository;
import guru.springframework.spjpaintro.repositories.BookRepository;
import guru.springframework.spjpaintro.repositories.BookUuidRepository;

@DataJpaTest
@ComponentScan(basePackages = "guru.springframework.spjpaintro.bootstrap")
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MySQLIntegrationTest {
	@Autowired BookRepository bookRepository;
	@Autowired BookUuidRepository bookUuidRepository;
	@Autowired BookNaturalRepository bookNaturalRepository;
	@Autowired AuthorUuidRepository authorUuidRepository;

	@Test
	void testMySQL() {
		long countBefore = bookRepository.count();
		assertThat(countBefore).isEqualTo(2);
	}

	@Test
	void testBookUuid() {
		long countBefore = bookUuidRepository.count();
		assertThat(countBefore).isEqualTo(2);
		String title = "My book";
		String isbn = "1234567890";
		String publisher = "My Publisher";

		BookUuid savedBook = bookUuidRepository.save(new BookUuid(title, isbn, publisher));
		assertThat(savedBook).isNotNull();
		assertThat(savedBook.getId()).isNotNull();

		long countAfter = bookUuidRepository.count();
		assertThat(countAfter).isEqualTo(3);

		Optional<BookUuid> foundBook = bookUuidRepository.findById(savedBook.getId());
		assertThat(foundBook).isPresent();
		assertThat(foundBook.get().getTitle()).isEqualTo(title);
		assertThat(foundBook.get().getIsbn()).isEqualTo(isbn);
		assertThat(foundBook.get().getPublisher()).isEqualTo(publisher);
	}

	@Test
	void testAuthorUuid() {
		long countBefore = authorUuidRepository.count();
		assertThat(countBefore).isEqualTo(1);
		String firstName = "Name";
		String lastName = "Surname";

		AuthorUuid savedAuthor = authorUuidRepository.save(new AuthorUuid(firstName, lastName));
		assertThat(savedAuthor).isNotNull();
		assertThat(savedAuthor.getId()).isNotNull();

		long countAfter = authorUuidRepository.count();
		assertThat(countAfter).isEqualTo(2);

		Optional<AuthorUuid> foundAuthor = authorUuidRepository.findById(savedAuthor.getId());
		assertThat(foundAuthor).isPresent();
		assertThat(foundAuthor.get().getFirstName()).isEqualTo(firstName);
		assertThat(foundAuthor.get().getLastName()).isEqualTo(lastName);
	}

	@Test
	void testBookNatural() {
		long countBefore = bookNaturalRepository.count();
		assertThat(countBefore).isEqualTo(0);
		String title = "My book";
		String isbn = "1234567890";
		String publisher = "My Publisher";

		BookNatural savedBook = bookNaturalRepository.save(new BookNatural(title, isbn, publisher));
		assertThat(savedBook).isNotNull();

		long countAfter = bookNaturalRepository.count();
		assertThat(countAfter).isEqualTo(1);

		Optional<BookNatural> foundBook = bookNaturalRepository.findById(title);
		assertThat(foundBook).isPresent();
		assertThat(foundBook.get().getTitle()).isEqualTo(title);
		assertThat(foundBook.get().getIsbn()).isEqualTo(isbn);
		assertThat(foundBook.get().getPublisher()).isEqualTo(publisher);
	}
}
