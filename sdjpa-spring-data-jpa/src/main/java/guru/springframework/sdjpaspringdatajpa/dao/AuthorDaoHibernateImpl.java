package guru.springframework.sdjpaspringdatajpa.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import guru.springframework.sdjpaspringdatajpa.domain.Author;
import guru.springframework.sdjpaspringdatajpa.domain.Author_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorDaoHibernateImpl implements AuthorDao {
	private final EntityManager entityManager;

	@Override
	public List<Author> findAll() {
		TypedQuery<Author> query = entityManager.createNamedQuery(Author_.QUERY_AUTHOR_FIND_ALL, Author.class);
		return query.getResultList();
	}

	@Override
	public List<Author> findAllByLastNameSortByFirstName(String lastName, Pageable pageable) {
		String jpql = "FROM Author WHERE lastName LIKE :lastName";
		if(pageable.getSort() != null && pageable.getSort().getOrderFor(Author_.FIRST_NAME) != null) {
			jpql += " ORDER BY firstName " + pageable.getSort().getOrderFor(Author_.FIRST_NAME).getDirection();
		}

		TypedQuery<Author> query = entityManager.createQuery(jpql, Author.class);
		query.setParameter("lastName", lastName);
		query.setFirstResult(Math.toIntExact(pageable.getOffset()));
		query.setMaxResults(Math.toIntExact(pageable.getPageSize()));
		return query.getResultList();
	}

	@Override
	public Optional<Author> getById(Long id) {
		return Optional.ofNullable(entityManager.find(Author.class, id));
	}

	@Override
	public Optional<Author> findAuthorByName(String firstName, String lastName) {
		TypedQuery<Author> query = entityManager.createNamedQuery(Author_.QUERY_AUTHOR_FIND_BY_NAME, Author.class);
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		return query.getResultStream().findFirst();
	}

	@Override
	public Optional<Author> findAuthorByNameCriteria(String firstName, String lastName) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Author> cq = cb.createQuery(Author.class);
		Root<Author> root = cq.from(Author.class);
		cq = cq.select(root)
		.where(
			cb.and(
				cb.equal(root.get(Author_.firstName), firstName),
				cb.equal(root.get(Author_.lastName), lastName)
			)
		);
		TypedQuery<Author> query = entityManager.createQuery(cq);
		return query.getResultStream().findFirst();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Optional<Author> findAuthorByNameNative(String firstName, String lastName) {
		Query query = entityManager.createNativeQuery("SELECT * FROM author a WHERE a.first_name = :firstName AND a.last_name = :lastName", Author.class);
		query.setParameter("firstName", firstName);
		query.setParameter("lastName", lastName);
		return query.getResultStream().findFirst();
	}

	@Override
	public List<Author> listAuthorByLastNameLike(String lastName) {
		TypedQuery<Author> query = entityManager.createNamedQuery(Author_.QUERY_AUTHOR_FIND_BY_LAST_NAME, Author.class);
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
