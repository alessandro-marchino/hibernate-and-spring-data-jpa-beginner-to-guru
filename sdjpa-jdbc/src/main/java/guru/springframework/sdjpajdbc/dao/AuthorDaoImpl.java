package guru.springframework.sdjpajdbc.dao;

import java.util.Optional;

import org.springframework.stereotype.Component;

import guru.springframework.sdjpajdbc.domain.Author;

@Component
public class AuthorDaoImpl implements AuthorDao {

	@Override
	public Optional<Author> getById(Long id) {
		return Optional.empty();
	}
}
