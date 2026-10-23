package guru.springframework.sdjpahibernatedao.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpahibernatedao.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableJpaRepositories
@Component
public class AuthorDaoImpl implements AuthorDao {
	private final EntityManager entityManager;

	@Override
	public Optional<Author> getById(Long id) {
		return Optional.ofNullable(entityManager.find(Author.class, id));
	}

	@Override
	public Optional<Author> findAuthorByName(String firstName, String lastName) {
		TypedQuery<Author> query = entityManager.createQuery("SELECT a FROM Author a WHERE a.firstName = :firstName AND a.lastName = :lastName", Author.class);
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		return query.getResultStream().findFirst();
	}

	@Override
	public Author saveNewAuthor(Author author) {
		// entityManager.joinTransaction();
		entityManager.persist(author);
		entityManager.flush();
		return author;
	}

	@Override
	public Author updateAuthor(Author author) {
		// em.joinTransaction();
		entityManager.merge(author);
		entityManager.flush();
		entityManager.clear();
		return entityManager.find(Author.class, author.getId());
	}

	@Override
	public void deleteAuthorById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteAuthorById'");
	}

}
