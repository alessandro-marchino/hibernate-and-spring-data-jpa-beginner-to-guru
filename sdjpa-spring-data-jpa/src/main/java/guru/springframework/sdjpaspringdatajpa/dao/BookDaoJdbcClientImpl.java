package guru.springframework.sdjpaspringdatajpa.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import guru.springframework.sdjpaspringdatajpa.domain.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class BookDaoJdbcClientImpl implements BookDao {

	private final JdbcClient jdbcClient;
	private final BookMapper bookMapper = new BookMapper();

	@Override
	public List<Book> findAll() {
		return jdbcClient.sql("SELECT * FROM book")
			.query(bookMapper)
			.list();
	}

	@Override
	public List<Book> findAll(int pageSize, int offset) {
		return jdbcClient.sql("SELECT * FROM book LIMIT :limit OFFSET :offset")
			.param("limit", pageSize)
			.param("offset", offset)
			.query(bookMapper)
			.list();
	}

	@Override
	public List<Book> findAll(Pageable pageable) {
		return jdbcClient.sql("SELECT * FROM book LIMIT :limit OFFSET :offset")
			.param("limit", pageable.getPageSize())
			.param("offset", pageable.getOffset())
			.query(bookMapper)
			.list();
	}

	@Override
	public List<Book> findAllSortByTitle(Pageable pageable) {
		return jdbcClient.sql("SELECT * FROM book ORDER BY title " + pageable.getSort().getOrderFor("title").getDirection() + " LIMIT :limit OFFSET :offset")
			.param("limit", pageable.getPageSize())
			.param("offset", pageable.getOffset())
			.query(bookMapper)
			.list();
	}

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
	public Optional<Book> getByTitleNative(String title) {
		return getByTitle(title);
	}

	@Override
	public Optional<Book> getByTitleCriteria(String title) {
		return getByTitle(title);
	}

	@Override
	public Optional<Book> findByISBN(String isbn) {
		return jdbcClient.sql("SELECT * FROM book WHERE isbn = :isbn")
			.param("isbn", isbn)
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
			.param("authorId", dto.getAuthorId())
			.update(generatedKeyHolder);

		return getById(generatedKeyHolder.getKey().longValue()).orElseThrow();
	}

	@Override
	public Book update(Book dto) {
		jdbcClient.sql("UPDATE book SET title = :title, isbn = :isbn, publisher = :publisher, author_id = :authorId WHERE id = :id")
			.param("title", dto.getTitle())
			.param("isbn", dto.getIsbn())
			.param("publisher", dto.getPublisher())
			.param("authorId", dto.getAuthorId())
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

	public static class BookMapper implements RowMapper<Book> {
		@Override
		public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			return Book.builder()
				.id(resultSet.getObject("id", Long.class))
				.isbn(resultSet.getString("isbn"))
				.publisher(resultSet.getString("publisher"))
				.title(resultSet.getString("title"))
				.authorId(resultSet.getObject("author_id", Long.class))
				.build();
		}
	}

}
