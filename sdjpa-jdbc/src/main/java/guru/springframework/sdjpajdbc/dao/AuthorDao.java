package guru.springframework.sdjpajdbc.dao;

import java.util.Optional;

import guru.springframework.sdjpajdbc.domain.Author;

public interface AuthorDao {

	Optional<Author> getById(Long id);
	Optional<Author> getByName(String firstName, String lastName);
	Author save(Author dto);

}
