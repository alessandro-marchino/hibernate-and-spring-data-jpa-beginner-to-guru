package guru.springframework.sdjpajdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpajdbc.domain.Author;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorDaoImpl implements AuthorDao {

	private final DataSource datasource;

	@Override
	public Optional<Author> getById(Long id) {
		Connection connection = DataSourceUtils.getConnection(datasource);
		try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM author WHERE id = ?")) {
			statement.setLong(1, id);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) {
					return Optional.of(getAuthorFromResultSet(resultSet));
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
	public Optional<Author> getByName(String firstName, String lastName) {
		Connection connection = DataSourceUtils.getConnection(datasource);
		try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM author WHERE first_name = ? AND last_name = ?")) {
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) {
					return Optional.of(getAuthorFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			log.error("Error in getByName: {}", e.getMessage(), e);
		} finally {
			DataSourceUtils.releaseConnection(connection, datasource);
		}
		return Optional.empty();
	}

	@Override
	public Author save(Author dto) {
		Connection connection = DataSourceUtils.getConnection(datasource);
		try(PreparedStatement statement = connection.prepareStatement("INSERT INTO author(first_name, last_name) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, dto.getFirstName());
			statement.setString(2, dto.getLastName());
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
	public Author update(Author dto) {
		Connection connection = DataSourceUtils.getConnection(datasource);
		try(PreparedStatement statement = connection.prepareStatement("UPDATE author SET first_name = ?, last_name = ? WHERE id = ?")) {
			statement.setString(1, dto.getFirstName());
			statement.setString(2, dto.getLastName());
			statement.setLong(3, dto.getId());
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
		try(PreparedStatement statement = connection.prepareStatement("DELETE FROM author WHERE id = ?")) {
			statement.setLong(1, id);
			statement.execute();
		} catch (SQLException e) {
			log.error("Error in deleteById: {}", e.getMessage(), e);
		} finally {
			DataSourceUtils.releaseConnection(connection, datasource);
		}
	}

	private Author getAuthorFromResultSet(ResultSet rs) throws SQLException {
		return Author.builder()
			.id(rs.getLong("id"))
			.firstName(rs.getString("first_name"))
			.lastName(rs.getString("last_name"))
			.build();
	}

}
