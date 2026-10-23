package guru.springframework.sdjpahibernatedao.dao;

import java.util.Optional;

import guru.springframework.sdjpahibernatedao.domain.Author;

public interface AuthorDao {

	Optional<Author> getById(Long id);

	Optional<Author> findAuthorByName(String firstName, String lastName);

	Author saveNewAuthor(Author author);

	Author updateAuthor(Author author);

	void deleteAuthorById(Long id);
}
