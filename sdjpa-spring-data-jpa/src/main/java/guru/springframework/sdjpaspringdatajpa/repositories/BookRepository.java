package guru.springframework.sdjpaspringdatajpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaspringdatajpa.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	// Empty
}
