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
					return Optional.of(Author.builder()
						.id(id)
						.firstName(resultSet.getString("first_name"))
						.lastName(resultSet.getString("last_name"))
						.build());
				}
			}
		} catch (SQLException e) {
			log.error("Error in getById: {}", e.getMessage(), e);
		}
		return Optional.empty();
	}
}
