package guru.springframework.sdjpaspringdatajpa.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import guru.springframework.sdjpaspringdatajpa.domain.Author;

public interface AuthorDao {

	Optional<Author> getById(Long id);

	Optional<Author> findAuthorByName(String firstName, String lastName);

	Optional<Author> findAuthorByNameCriteria(String firstName, String lastName);

	Optional<Author> findAuthorByNameNative(String firstName, String lastName);

	Author saveNewAuthor(Author author);

	Author updateAuthor(Author author);

	void deleteAuthorById(Long id);

	List<Author> listAuthorByLastNameLike(String lastName);

	List<Author> findAll();

	List<Author> findAllByLastNameSortByFirstName(String lastName, Pageable pageable);
}
