package guru.springframework.sdjpaspringdatajpa.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.sdjpaspringdatajpa.domain.Author;
import guru.springframework.sdjpaspringdatajpa.domain.Author_;
import guru.springframework.sdjpaspringdatajpa.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthorDaoImpl implements AuthorDao {
	private final AuthorRepository authorRepository;

	@Override
	public List<Author> findAll() {
		return authorRepository.findAll();
	}

	@Override
	public Optional<Author> getById(Long id) {
		return authorRepository.findById(id);
	}

	@Override
	public Optional<Author> findAuthorByName(String firstName, String lastName) {
		return authorRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	@Override
	public Optional<Author> findAuthorByNameCriteria(String firstName, String lastName) {
		return authorRepository.findOne((author, cq, cb) -> cb.and(
			cb.equal(author.get(Author_.firstName), firstName),
			cb.equal(author.get(Author_.lastName), lastName)
		));
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
	@Transactional
	public Author saveNewAuthor(Author author) {
		return authorRepository.saveAndFlush(author);
	}

	@Override
	@Transactional
	public Author updateAuthor(Author author) {
		Author foundAuthor = authorRepository.findById(author.getId()).orElseThrow();
		foundAuthor.setFirstName(author.getFirstName());
		foundAuthor.setLastName(author.getLastName());
		return authorRepository.saveAndFlush(foundAuthor);
	}

	@Override
	@Transactional
	public void deleteAuthorById(Long id) {
		authorRepository.deleteById(id);
	}

}
