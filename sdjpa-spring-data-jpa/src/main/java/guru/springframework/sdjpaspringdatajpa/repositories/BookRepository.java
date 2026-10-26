package guru.springframework.sdjpaspringdatajpa.repositories;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import guru.springframework.sdjpaspringdatajpa.domain.Book;
import guru.springframework.sdjpaspringdatajpa.domain.Book_;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

	Optional<Book> findByTitle(String title);

	@Query(name = Book_.QUERY_BOOK_FIND_BY_TITLE)
	Optional<Book> findByTitleNamed(@Param("title") String title);

	Optional<Book> jpaNamed(@Param("title") String title);

	@Query(value = "SELECT id, title, isbn, publisher, author_id FROM book WHERE title = :title", nativeQuery = true)
	Optional<Book> findByTitleNative(@Param("title") String title);

	Optional<Book> findByIsbn(String isbn);

	@NullMarked Book readByTitle(String title);

	@Nullable Book getByTitle(@Nullable String title);

	Stream<Book> findAllByTitleNotNull();

	@Async Future<Book> queryByTitle(String title);

	@Query("FROM Book WHERE title = :title")
	Optional<Book> findBooByTitleWithQuery(@Param("title") String title);
}
