package guru.springframework.spjpaintro.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import guru.springframework.spjpaintro.domain.AuthorUuid;
import guru.springframework.spjpaintro.domain.Book;
import guru.springframework.spjpaintro.domain.BookUuid;
import guru.springframework.spjpaintro.repositories.AuthorUuidRepository;
import guru.springframework.spjpaintro.repositories.BookRepository;
import guru.springframework.spjpaintro.repositories.BookUuidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({ "local", "default" })
public class DataInitializer implements CommandLineRunner {
	private final BookRepository bookRepository;
	private final BookUuidRepository bookUuidRepository;
	private final AuthorUuidRepository authorUuidRepository;

	@Override
	public void run(String... args) throws Exception {
		createBooks();
		createBooksUuid();
		createAuthorsUuid();
	}

	private void createBooks() {
		long count = bookRepository.count();
		if(count != 0) {
			return;
		}
		bookRepository.save(new Book("Domain Drive Design", "123", "Random House", null));
		bookRepository.save(new Book("Spring In Action", "234234", "O'Reilly", null));

		bookRepository.findAll().forEach(book -> {
			log.info("Book Id: {} - title: {}", book.getId(), book.getTitle());
		});
	}

	private void createBooksUuid() {
		long count = bookUuidRepository.count();
		if(count != 0) {
			return;
		}
		bookUuidRepository.save(new BookUuid("Domain Drive Design", "123", "Random House"));
		bookUuidRepository.save(new BookUuid("Spring In Action", "234234", "O'Reilly"));

		bookUuidRepository.findAll().forEach(book -> {
			log.info("Book Id: {} - title: {}", book.getId(), book.getTitle());
		});
	}

	private void createAuthorsUuid() {
		long count = authorUuidRepository.count();
		if(count != 0) {
			return;
		}
		AuthorUuid savedAuthor = authorUuidRepository.save(new AuthorUuid("Joe", "Buck"));
		log.info("Saved author UUID: {}", savedAuthor.getId());
	}
}
