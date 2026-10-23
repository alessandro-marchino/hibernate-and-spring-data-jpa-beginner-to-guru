package guru.springframework.sdjpajdbc.dao;

import java.util.Optional;

import guru.springframework.sdjpajdbc.domain.Book;

public interface BookDao {

	Optional<Book> getById(Long id);
	Optional<Book> getByTitle(String title);
	Book save(Book dto);
	Book update(Book dto);
	void deleteById(Long id);
}
