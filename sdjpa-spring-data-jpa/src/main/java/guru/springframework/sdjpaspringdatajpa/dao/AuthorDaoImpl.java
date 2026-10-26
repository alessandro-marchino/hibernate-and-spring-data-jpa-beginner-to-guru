package guru.springframework.sdjpaspringdatajpa.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpaspringdatajpa.domain.Author;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableJpaRepositories
@Component
public class AuthorDaoImpl implements AuthorDao {

	@Override
	public List<Author> findAll() {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Optional<Author> getById(Long id) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Optional<Author> findAuthorByName(String firstName, String lastName) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Optional<Author> findAuthorByNameCriteria(String firstName, String lastName) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Optional<Author> findAuthorByNameNative(String firstName, String lastName) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public List<Author> listAuthorByLastNameLike(String lastName) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Author saveNewAuthor(Author author) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Author updateAuthor(Author author) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void deleteAuthorById(Long id) {
		throw new UnsupportedOperationException("Not implemented");
	}

}
