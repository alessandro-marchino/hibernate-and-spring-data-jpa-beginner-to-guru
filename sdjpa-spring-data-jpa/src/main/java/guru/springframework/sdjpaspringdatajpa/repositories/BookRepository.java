package guru.springframework.sdjpaspringdatajpa.repositories;

import java.util.Optional;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import guru.springframework.sdjpaspringdatajpa.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

	Optional<Book> findByTitle(String title);

	Optional<Book> findByIsbn(String isbn);

	@NullMarked Book readByTitle(String title);
	@Nullable Book getByTitle(@Nullable String title);
}
