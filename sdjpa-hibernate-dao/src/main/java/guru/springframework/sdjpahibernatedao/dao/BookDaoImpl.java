package guru.springframework.sdjpahibernatedao.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import guru.springframework.sdjpahibernatedao.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
	private final EntityManager em;

	@Override
	public List<Book> findAll() {
		TypedQuery<Book> query = em.createNamedQuery("Book.findAll", Book.class);
		return query.getResultList();
	}

	@Override
	public Optional<Book> getById(Long id) {
		return Optional.ofNullable(em.find(Book.class, id));
	}

	@Override
	public Optional<Book> getByTitle(String title) {
		TypedQuery<Book> query = em.createNamedQuery("Book.findByTitle", Book.class);
		query.setParameter("title", title);
		return query.getResultStream().findFirst();
	}

	@Override
	public Optional<Book> findByISBN(String isbn) {
		TypedQuery<Book> query = em.createNamedQuery("Book.findByIsbn", Book.class);
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
