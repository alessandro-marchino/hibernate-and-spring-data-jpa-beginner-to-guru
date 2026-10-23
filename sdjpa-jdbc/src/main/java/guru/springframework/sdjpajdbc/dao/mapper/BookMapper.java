package guru.springframework.sdjpajdbc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpajdbc.dao.AuthorDao;
import guru.springframework.sdjpajdbc.domain.Author;
import guru.springframework.sdjpajdbc.domain.Book;
import lombok.RequiredArgsConstructor;

public interface BookMapper extends RowMapper<Book> {

	@Component
	@RequiredArgsConstructor
	public static class BookMapperImpl implements BookMapper {
		private final AuthorDao authorDao;

		@Override
		public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			Long authorId = resultSet.getObject("author_id", Long.class);
			Author author = authorId == null ? null : authorDao.getById(authorId).orElseThrow();
			return Book.builder()
				.id(resultSet.getObject("id", Long.class))
				.isbn(resultSet.getString("isbn"))
				.publisher(resultSet.getString("publisher"))
				.title(resultSet.getString("title"))
				.author(author)
				.build();
		}
	}
}
