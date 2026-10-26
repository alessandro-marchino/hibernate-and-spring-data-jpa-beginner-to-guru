package guru.springframework.sdjpaspringdatajpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import guru.springframework.sdjpaspringdatajpa.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
	Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);

	List<Author> findAllByLastNameContaining(String lastName);

	@Query(value = "SELECT id, first_name, last_name FROM author WHERE first_name = :firstName AND last_name = :lastName", nativeQuery = true)
	Optional<Author> findByNameNative(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
