package guru.springframework.sdjpajdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpajdbc.domain.Author;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorDaoImpl implements AuthorDao {

	private final NamedParameterJdbcOperations jdbcTemplate;

	@Override
	public Optional<Author> getById(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		return jdbcTemplate.query("SELECT * FROM author WHERE id = :id", params, this::getAuthorFromResultSet)
			.stream()
			.findFirst();
	}

	@Override
	public Optional<Author> getByName(String firstName, String lastName) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("firstName", firstName);
		params.addValue("lastName", lastName);
		return jdbcTemplate.query("SELECT * FROM author WHERE first_name = :firstName AND last_name = :lastName", params, this::getAuthorFromResultSet)
			.stream()
			.findFirst();
	}

	@Override
	public Author save(Author dto) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("firstName", dto.getFirstName());
		params.addValue("lastName", dto.getLastName());
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update("INSERT INTO author(first_name, last_name) VALUES (:firstName, :lastName)", params, generatedKeyHolder);
		return getById(generatedKeyHolder.getKey().longValue()).orElseThrow();
	}

	@Override
	public Author update(Author dto) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("firstName", dto.getFirstName());
		params.addValue("lastName", dto.getLastName());
		params.addValue("id", dto.getId());

		jdbcTemplate.update("UPDATE author SET first_name = :firstName, last_name = :lastName WHERE id = :id", params);
		return getById(dto.getId()).orElseThrow();
	}

	@Override
	public void deleteById(Long id) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);

		jdbcTemplate.update("DELETE FROM author WHERE id = :id", params);
	}

	private Author getAuthorFromResultSet(ResultSet rs, int rowNum) throws SQLException {
		return Author.builder()
			.id(rs.getLong("id"))
			.firstName(rs.getString("first_name"))
			.lastName(rs.getString("last_name"))
			.build();
	}

}
