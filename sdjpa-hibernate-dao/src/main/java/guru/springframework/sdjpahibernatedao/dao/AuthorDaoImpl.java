package guru.springframework.sdjpahibernatedao.dao;

import java.util.List;
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
	public List<Author> findAll() {
		TypedQuery<Author> query = entityManager.createNamedQuery("Author.findAll", Author.class);
		return query.getResultList();
	}

	@Override
	public Optional<Author> getById(Long id) {
		return Optional.ofNullable(entityManager.find(Author.class, id));
	}

	@Override
	public Optional<Author> findAuthorByName(String firstName, String lastName) {
		TypedQuery<Author> query = entityManager.createNamedQuery("Author.findByName", Author.class);
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		return query.getResultStream().findFirst();
	}

	@Override
	public List<Author> listAuthorByLastNameLike(String lastName) {
		TypedQuery<Author> query = entityManager.createNamedQuery("Author.findByLastName", Author.class);
		query.setParameter("lastName", lastName + "%");
		return query.getResultList();
	}

	@Override
	public Author saveNewAuthor(Author author) {
		entityManager.persist(author);
		entityManager.flush();
		return author;
	}

	@Override
	public Author updateAuthor(Author author) {
		entityManager.merge(author);
		entityManager.flush();
		entityManager.clear();
		return entityManager.find(Author.class, author.getId());
	}

	@Override
	public void deleteAuthorById(Long id) {
		Author author = entityManager.find(Author.class, id);
		if(author != null) {
			entityManager.remove(author);
			entityManager.flush();
		}
	}

}
