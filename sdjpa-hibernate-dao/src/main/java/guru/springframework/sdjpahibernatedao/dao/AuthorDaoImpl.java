package guru.springframework.sdjpahibernatedao.dao;

import java.util.Optional;

import org.springframework.stereotype.Component;

import guru.springframework.sdjpahibernatedao.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthorDaoImpl implements AuthorDao {
	private final EntityManagerFactory emf;

	@Override
	public Optional<Author> getById(Long id) {
		return Optional.ofNullable(getEntityManager().find(Author.class, id));
	}

	@Override
	public Optional<Author> findAuthorByName(String firstName, String lastName) {
		TypedQuery<Author> query = getEntityManager().createQuery("SELECT a FROM Author a WHERE a.firstName = :firstName AND a.lastName = :lastName", Author.class);
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		return query.getResultStream().findFirst();
	}

	@Override
	public Author saveNewAuthor(Author author) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'saveNewAuthor'");
	}

	@Override
	public Author updateAuthor(Author author) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updateAuthor'");
	}

	@Override
	public void deleteAuthorById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteAuthorById'");
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
