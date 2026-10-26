package guru.springframework.sdjpahibernatedao.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import guru.springframework.sdjpahibernatedao.domain.Book;
import guru.springframework.sdjpahibernatedao.domain.Book_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
	private final EntityManager em;

	@Override
	public List<Book> findAll() {
		TypedQuery<Book> query = em.createNamedQuery(Book_.QUERY_BOOK_FIND_ALL, Book.class);
		return query.getResultList();
	}

	@Override
	public Optional<Book> getById(Long id) {
		return Optional.ofNullable(em.find(Book.class, id));
	}

	@Override
	public Optional<Book> getByTitle(String title) {
		TypedQuery<Book> query = em.createNamedQuery(Book_.QUERY_BOOK_FIND_BY_TITLE, Book.class);
		query.setParameter("title", title);
		return query.getResultStream().findFirst();
	}

	@Override
	public Optional<Book> getByTitleCriteria(String title) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Book> cq = cb.createQuery(Book.class);
		Root<Book> root = cq.from(Book.class);
		cq = cq.select(root)
		.where(
			cb.equal(root.get(Book_.title), title)
		);
		TypedQuery<Book> query = em.createQuery(cq);
		return query.getResultStream().findFirst();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<Book> getByTitleNative(String title) {
		Query query = em.createNativeQuery("SELECT * FROM book b WHERE b.title = :title", Book.class);
		query.setParameter("title", title);
		return query.getResultStream().findFirst();
	}

	@Override
	public Optional<Book> findByISBN(String isbn) {
		TypedQuery<Book> query = em.createNamedQuery(Book_.QUERY_BOOK_FIND_BY_ISBN, Book.class);
		query.setParameter("isbn", isbn);
		return query.getResultStream().findFirst();
	}

	@Override
	public Book save(Book dto) {
		em.persist(dto);
		em.flush();
		return dto;
	}

	@Override
	public Book update(Book dto) {
		em.merge(dto);
		em.flush();
		em.clear();
		return em.find(Book.class, dto.getId());
	}

	@Override
	public void deleteById(Long id) {
		getById(id).ifPresent(book -> {
			em.remove(book);
			em.flush();
		});
	}

}
