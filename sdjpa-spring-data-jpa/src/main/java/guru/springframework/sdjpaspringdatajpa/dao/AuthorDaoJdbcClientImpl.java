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

import guru.springframework.sdjpaspringdatajpa.domain.Author;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AuthorDaoJdbcClientImpl implements AuthorDao {

	private final JdbcClient jdbcClient;
	private final AuthorMapper authorMapper = new AuthorMapper();

	@Override
	public Optional<Author> getById(Long id) {
		return jdbcClient.sql("SELECT * FROM author WHERE id = :id")
			.param("id", id)
			.query(authorMapper)
			.optional();
	}

	@Override
	public List<Author> findAllByLastNameSortByFirstName(String lastName, Pageable pageable) {
		String sql = "SELECT * FROM author WHERE last_name = :lastName";
		if(pageable.getSort() != null && pageable.getSort().getOrderFor("firstName") != null) {
			sql += " ORDER BY first_name " + pageable.getSort().getOrderFor("firstName").getDirection();
		}
		sql += " LIMIT :limit OFFSET :offset";

		return jdbcClient.sql(sql)
			.param("lastName", lastName)
			.param("limit", pageable.getPageSize())
			.param("offset", pageable.getOffset())
			.query(authorMapper)
			.list();
	}

	@Override
	public Optional<Author> findAuthorByName(String firstName, String lastName) {
		return jdbcClient.sql("SELECT * FROM author WHERE first_name = :firstName AND last_name = :lastName")
			.param("firstName", firstName)
			.param("lastName", lastName)
			.query(authorMapper)
			.optional();
	}

	@Override
	public Optional<Author> findAuthorByNameCriteria(String firstName, String lastName) {
		return findAuthorByName(firstName, lastName);
	}

	@Override
	public Optional<Author> findAuthorByNameNative(String firstName, String lastName) {
		return findAuthorByName(firstName, lastName);
	}

	@Override
	public Author saveNewAuthor(Author dto) {
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcClient.sql("INSERT INTO author(first_name, last_name) VALUES (:firstName, :lastName)")
			.param("firstName", dto.getFirstName())
			.param("lastName", dto.getLastName())
			.update(generatedKeyHolder);

		return getById(generatedKeyHolder.getKey().longValue()).orElseThrow();
	}

	@Override
	public Author updateAuthor(Author dto) {
		jdbcClient.sql("UPDATE author SET first_name = :firstName, last_name = :lastName WHERE id = :id")
			.param("firstName", dto.getFirstName())
			.param("lastName", dto.getLastName())
			.param("id", dto.getId())
			.update();

		return getById(dto.getId()).orElseThrow();
	}

	@Override
	public void deleteAuthorById(Long id) {
		jdbcClient.sql("DELETE FROM author WHERE id = :id")
			.param("id", id)
			.update();
	}

	@Override
	public List<Author> listAuthorByLastNameLike(String lastName) {
		return jdbcClient.sql("SELECT * FROM author WHERE last_name LIKE :lastName")
			.param("lastName", "%" + lastName + "%")
			.query(authorMapper)
			.list();
	}

	@Override
	public List<Author> findAll() {
		return jdbcClient.sql("SELECT * FROM author")
			.query(authorMapper)
			.list();
	}

	public static class AuthorMapper implements RowMapper<Author> {
		@Override
		public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
			return Author.builder()
				.id(rs.getLong("id"))
				.firstName(rs.getString("first_name"))
				.lastName(rs.getString("last_name"))
				.build();
		}
	}
}
