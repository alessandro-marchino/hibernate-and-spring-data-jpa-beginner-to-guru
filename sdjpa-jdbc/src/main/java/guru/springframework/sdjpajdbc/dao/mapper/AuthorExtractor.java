package guru.springframework.sdjpajdbc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpajdbc.domain.Author;
import guru.springframework.sdjpajdbc.domain.Book;

public interface AuthorExtractor extends ResultSetExtractor<Optional<Author>> {

	@Component
	public static class AuthorExtractorImpl implements AuthorExtractor {
		@Override
		public Optional<Author> extractData(ResultSet rs) throws SQLException, DataAccessException {
			if(!rs.next()) {
				return Optional.empty();
			}
			// First execution: map author
			Author author = Author.builder()
				.id(rs.getLong("id"))
				.firstName(rs.getString("first_name"))
				.lastName(rs.getString("last_name"))
				.books(new ArrayList<>())
				.build();
			addBook(author, rs);
			// Following executions - map only books
			while(rs.next()) {
				addBook(author, rs);
			}
			return Optional.of(author);
		}

		private void addBook(Author author, ResultSet rs) throws SQLException {
			Long bookId = rs.getObject("book_id", Long.class);
			if(bookId == null) {
				return;
			}
			Book book = Book.builder()
				.id(rs.getObject("book_id", Long.class))
				.isbn(rs.getString("isbn"))
				.publisher(rs.getString("publisher"))
				.title(rs.getString("title"))
				.build();
			author.getBooks().add(book);
		}
	}
}
