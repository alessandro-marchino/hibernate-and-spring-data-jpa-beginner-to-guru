package guru.springframework.sdjpaspringdatajpa.dao;

import java.util.List;
import java.util.Optional;

import guru.springframework.sdjpaspringdatajpa.domain.Book;

public interface BookDao {

	List<Book> findAll();
	List<Book> findAll(int pageSize, int offset);
	Optional<Book> getById(Long id);
	Optional<Book> getByTitle(String title);
	Optional<Book> getByTitleCriteria(String title);
	Optional<Book> getByTitleNative(String title);
	Optional<Book> findByISBN(String isbn);
	Book save(Book dto);
	Book update(Book dto);
	void deleteById(Long id);

}
