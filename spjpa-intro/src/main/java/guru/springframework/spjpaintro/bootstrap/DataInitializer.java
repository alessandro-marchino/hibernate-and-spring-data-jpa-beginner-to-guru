package guru.springframework.spjpaintro.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import guru.springframework.spjpaintro.domain.Book;
import guru.springframework.spjpaintro.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile({ "local", "default" })
public class DataInitializer implements CommandLineRunner {
	private final BookRepository bookRepository;

	@Override
	public void run(String... args) throws Exception {
		long count = bookRepository.count();
		if(count != 0) {
			return;
		}
		bookRepository.save(new Book("Domain Drive Design", "123", "Random House"));
		bookRepository.save(new Book("Spring In Action", "234234", "O'Reilly"));

		bookRepository.findAll().forEach(book -> {
			log.info("Book Id: {} - title: {}", book.getId(), book.getTitle());
		});
	}
}
