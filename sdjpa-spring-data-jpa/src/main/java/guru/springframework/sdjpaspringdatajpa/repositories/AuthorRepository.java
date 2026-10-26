package guru.springframework.sdjpaspringdatajpa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import guru.springframework.sdjpaspringdatajpa.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
	// Empty
	Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
