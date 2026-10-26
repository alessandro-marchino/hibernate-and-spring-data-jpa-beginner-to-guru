package guru.springframework.sdjpaspringdatajpa.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import guru.springframework.sdjpaspringdatajpa.domain.Book;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

	@Override
	public List<Book> findAll() {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Optional<Book> getById(Long id) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Optional<Book> getByTitle(String title) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Optional<Book> getByTitleCriteria(String title) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Optional<Book> getByTitleNative(String title) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Optional<Book> findByISBN(String isbn) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Book save(Book dto) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Book update(Book dto) {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public void deleteById(Long id) {
		throw new UnsupportedOperationException("Not implemented");
	}

}
