package guru.springframework.sdjpajdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpajdbc.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	// Empty
}
