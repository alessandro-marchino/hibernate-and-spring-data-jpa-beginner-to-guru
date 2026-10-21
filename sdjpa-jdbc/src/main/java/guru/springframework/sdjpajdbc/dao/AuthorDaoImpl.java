package guru.springframework.sdjpajdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

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
		try(
				Connection connection = datasource.getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM author WHERE id = ?")) {
			statement.setLong(1, id);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) {
					return Optional.of(getAuthorFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			log.error("Error in getById: {}", e.getMessage(), e);
		}
		return Optional.empty();
	}

	@Override
	public Optional<Author> getByName(String firstName, String lastName) {
		try(
				Connection connection = datasource.getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM author WHERE first_name = ? AND last_name = ?")) {
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) {
					return Optional.of(getAuthorFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			log.error("Error in getByName: {}", e.getMessage(), e);
		}
		return Optional.empty();
	}

	private Author getAuthorFromResultSet(ResultSet rs) throws SQLException {
		return Author.builder()
			.id(rs.getLong("id"))
			.firstName(rs.getString("first_name"))
			.lastName(rs.getString("last_name"))
			.build();
	}

}
