package guru.springframework.sdjpajdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javax.sql.DataSource;

import org.hibernate.type.SqlTypes;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpajdbc.domain.Author;
import guru.springframework.sdjpajdbc.domain.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookDaoImpl implements BookDao {

	private final DataSource datasource;
	private final AuthorDao authorDao;

	@Override
	public Optional<Book> getById(Long id) {
		Connection connection = DataSourceUtils.getConnection(datasource);
		try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM book WHERE id = ?")) {
			statement.setLong(1, id);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) {
					return Optional.of(getBookFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			log.error("Error in getById: {}", e.getMessage(), e);
		} finally {
			DataSourceUtils.releaseConnection(connection, datasource);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Book> getByTitle(String title) {
		Connection connection = DataSourceUtils.getConnection(datasource);
		try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM book WHERE title = ?")) {
			statement.setString(1, title);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) {
					return Optional.of(getBookFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			log.error("Error in getByTitle: {}", e.getMessage(), e);
		} finally {
			DataSourceUtils.releaseConnection(connection, datasource);
		}
		return Optional.empty();
	}

	@Override
	public Book save(Book dto) {
		Connection connection = DataSourceUtils.getConnection(datasource);
		try(PreparedStatement statement = connection.prepareStatement("INSERT INTO book(title, isbn, publisher, author_id) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, dto.getTitle());
			statement.setString(2, dto.getIsbn());
			statement.setString(3, dto.getPublisher());
			if(dto.getAuthor() != null && dto.getAuthor().getId() != null) {
				statement.setLong(4, dto.getAuthor().getId());
			} else {
				statement.setNull(4, SqlTypes.BIGINT);
			}
			statement.execute();

			try(ResultSet rs = statement.getGeneratedKeys()) {
				if(rs.next()) {
					long id = rs.getLong(1);
					log.info("Inserted id: {}", id);
					return this.getById(id).orElseThrow();
				}
			}
		} catch (SQLException e) {
			log.error("Error in save: {}", e.getMessage(), e);
		} finally {
			DataSourceUtils.releaseConnection(connection, datasource);
		}
		return null;
	}

	@Override
	public Book update(Book dto) {
		Connection connection = DataSourceUtils.getConnection(datasource);
		try(PreparedStatement statement = connection.prepareStatement("UPDATE book SET title = ?, isbn = ?, publisher = ?, author_id = ? WHERE id = ?")) {
			statement.setString(1, dto.getTitle());
			statement.setString(2, dto.getIsbn());
			statement.setString(3, dto.getPublisher());
			if(dto.getAuthor() != null && dto.getAuthor().getId() != null) {
				statement.setLong(4, dto.getAuthor().getId());
			} else {
				statement.setNull(4, SqlTypes.BIGINT);
			}
			statement.setLong(5, dto.getId());
			statement.execute();
		} catch (SQLException e) {
			log.error("Error in update: {}", e.getMessage(), e);
		} finally {
			DataSourceUtils.releaseConnection(connection, datasource);
		}
		return this.getById(dto.getId()).orElseThrow();
	}

	@Override
	public void deleteById(Long id) {
		Connection connection = DataSourceUtils.getConnection(datasource);
		try(PreparedStatement statement = connection.prepareStatement("DELETE FROM book WHERE id = ?")) {
			statement.setLong(1, id);
			statement.execute();
		} catch (SQLException e) {
			log.error("Error in deleteById: {}", e.getMessage(), e);
		} finally {
			DataSourceUtils.releaseConnection(connection, datasource);
		}
	}

	private Book getBookFromResultSet(ResultSet resultSet) throws SQLException {
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
