package guru.springframework.sdjpajdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpajdbc.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	// Empty
}
