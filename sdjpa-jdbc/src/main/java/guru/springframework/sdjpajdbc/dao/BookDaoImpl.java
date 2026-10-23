package guru.springframework.sdjpajdbc.dao;

import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpajdbc.dao.mapper.BookMapper;
import guru.springframework.sdjpajdbc.domain.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {

	private final JdbcClient jdbcClient;
	private final BookMapper bookMapper;

	@Override
	public Optional<Book> getById(Long id) {
		return jdbcClient.sql("SELECT * FROM book WHERE id = :id")
			.param("id", id)
			.query(bookMapper)
			.optional();
	}

	@Override
	public Optional<Book> getByTitle(String title) {
		return jdbcClient.sql("SELECT * FROM book WHERE title = :title")
			.param("title", title)
			.query(bookMapper)
			.optional();
	}

	@Override
	public Book save(Book dto) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcClient.sql("INSERT INTO book(title, isbn, publisher, author_id) VALUES (:title, :isbn, :publisher, :authorId)")
			.param("title", dto.getTitle())
			.param("isbn", dto.getIsbn())
			.param("publisher", dto.getPublisher())
			.param("authorId", dto.getAuthor() != null ? dto.getAuthor().getId() : null)
			.update(generatedKeyHolder);

		return getById(generatedKeyHolder.getKey().longValue()).orElseThrow();
	}

	@Override
	public Book update(Book dto) {
		jdbcClient.sql("UPDATE book SET title = :title, isbn = :isbn, publisher = :publisher, author_id = :authorId WHERE id = :id")
			.param("title", dto.getTitle())
			.param("isbn", dto.getIsbn())
			.param("publisher", dto.getPublisher())
			.param("authorId", dto.getAuthor() != null ? dto.getAuthor().getId() : null)
			.param("id", dto.getId())
			.update();

		return getById(dto.getId()).orElseThrow();
	}

	@Override
	public void deleteById(Long id) {
		jdbcClient.sql("DELETE FROM book WHERE id = :id")
			.param("id", id)
			.update();
	}

}
