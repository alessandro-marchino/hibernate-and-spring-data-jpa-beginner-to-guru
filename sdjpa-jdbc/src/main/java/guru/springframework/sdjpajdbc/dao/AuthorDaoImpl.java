package guru.springframework.sdjpajdbc.dao;

import java.util.Optional;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpajdbc.dao.mapper.AuthorExtractor;
import guru.springframework.sdjpajdbc.domain.Author;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorDaoImpl implements AuthorDao {

	private final JdbcClient jdbcClient;
	private final AuthorExtractor authorExtractor;

	@Override
	public Optional<Author> getById(Long id) {
		return jdbcClient.sql("""
				SELECT author.id AS id, author.first_name, author.last_name,
					book.id AS book_id, book.isbn, book.publisher, book.title
				FROM author
				LEFT OUTER JOIN book ON author.id = book.author_id
				WHERE author.id = :id
				""")
			.param("id", id)
			.query(authorExtractor);
	}

	@Override
	public Optional<Author> getByName(String firstName, String lastName) {
		return jdbcClient.sql("""
				SELECT author.id AS id, author.first_name, author.last_name,
					book.id AS book_id, book.isbn, book.publisher, book.title
				FROM author
				LEFT OUTER JOIN book ON author.id = book.author_id
				WHERE author.first_name = :firstName
				AND author.last_name = :lastName
				""")
			.param("firstName", firstName)
			.param("lastName", lastName)
			.query(authorExtractor);
	}

	@Override
	public Author save(Author dto) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcClient.sql("INSERT INTO author(first_name, last_name) VALUES (:firstName, :lastName)")
			.param("firstName", dto.getFirstName())
			.param("lastName", dto.getLastName())
			.update(generatedKeyHolder);

		return getById(generatedKeyHolder.getKey().longValue()).orElseThrow();
	}

	@Override
	public Author update(Author dto) {
		jdbcClient.sql("UPDATE author SET first_name = :firstName, last_name = :lastName WHERE id = :id")
			.param("firstName", dto.getFirstName())
			.param("lastName", dto.getLastName())
			.param("id", dto.getId())
			.update();

		return getById(dto.getId()).orElseThrow();
	}

	@Override
	public void deleteById(Long id) {
		jdbcClient.sql("DELETE FROM author WHERE id = :id")
			.param("id", id)
			.update();
	}

}
