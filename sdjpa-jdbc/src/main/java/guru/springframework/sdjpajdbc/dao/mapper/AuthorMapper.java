package guru.springframework.sdjpajdbc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpajdbc.domain.Author;

public interface AuthorMapper extends RowMapper<Author> {

	@Component
	public static class AuthorMapperImpl implements AuthorMapper {
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
