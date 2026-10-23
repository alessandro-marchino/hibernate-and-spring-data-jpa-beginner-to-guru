package guru.springframework.sdjpahibernatedao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.sdjpahibernatedao.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	// Empty
}
