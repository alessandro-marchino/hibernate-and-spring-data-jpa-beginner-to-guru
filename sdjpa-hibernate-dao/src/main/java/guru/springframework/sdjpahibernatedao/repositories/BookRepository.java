package guru.springframework.sdjpahibernatedao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpahibernatedao.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	// Empty
}
