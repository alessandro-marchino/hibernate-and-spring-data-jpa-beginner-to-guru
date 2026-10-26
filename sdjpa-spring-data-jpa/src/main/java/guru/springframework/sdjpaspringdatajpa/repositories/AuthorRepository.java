package guru.springframework.sdjpaspringdatajpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpaspringdatajpa.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	// Empty
}
