package guru.springframework.sdjpaspringdatajpa.repositories;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.scheduling.annotation.Async;

import guru.springframework.sdjpaspringdatajpa.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

	Optional<Book> findByTitle(String title);

	Optional<Book> findByIsbn(String isbn);

	@NullMarked Book readByTitle(String title);
	@Nullable Book getByTitle(@Nullable String title);
	Stream<Book> findAllByTitleNotNull();
	@Async Future<Book> queryByTitle(String title);
}
