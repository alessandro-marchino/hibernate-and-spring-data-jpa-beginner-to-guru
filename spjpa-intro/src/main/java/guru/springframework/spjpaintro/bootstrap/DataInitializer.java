package guru.springframework.spjpaintro.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import guru.springframework.spjpaintro.domain.Book;
import guru.springframework.spjpaintro.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
	private final BookRepository bookRepository;

	@Override
	public void run(String... args) throws Exception {
		Book bookDDD = new Book("Domain Drive Design", "123", "Random House");
		log.info("Id: {}", bookDDD.getId());
		Book savedDDD = bookRepository.save(bookDDD);
		log.info("Id: {}", savedDDD.getId());

		Book bookSIA = new Book("Spring In Action", "234234", "O'Reilly");
		bookRepository.save(bookSIA);

		bookRepository.findAll().forEach(book -> {
			log.info("Book Id: {} - title: {}", book.getId(), book.getTitle());
		});
	}
}
