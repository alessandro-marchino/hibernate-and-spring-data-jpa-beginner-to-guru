package guru.springframework.sdjpaspringdatajpa.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import guru.springframework.sdjpaspringdatajpa.domain.Book;
import guru.springframework.sdjpaspringdatajpa.domain.Book_;
import guru.springframework.sdjpaspringdatajpa.repositories.BookRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Primary
public class BookDaoImpl implements BookDao {
	private final BookRepository bookRepository;

	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	@Override
	public List<Book> findAll(int pageSize, int offset) {
		return bookRepository.findAll(PageRequest.of((int)Math.floor(offset / pageSize), pageSize))
			.toList();
	}

	@Override
	public List<Book> findAll(Pageable pageable) {
		return bookRepository.findAll(pageable)
			.toList();
	}

	@Override
	public List<Book> findAllSortByTitle(Pageable pageable) {
		return bookRepository.findAll(pageable)
			.toList();
	}

	@Override
	public Optional<Book> getById(Long id) {
		return bookRepository.findById(id);
	}

	@Override
	public Optional<Book> getByTitle(String title) {
		return bookRepository.findByTitle(title);
	}

	@Override
	public Optional<Book> getByTitleCriteria(String title) {
		return bookRepository.findOne((book, cq, cb) -> cb.equal(book.get(Book_.title), title));
	}

	@Override
	public Optional<Book> getByTitleNative(String title) {
		return bookRepository.findByTitleNative(title);
	}

	@Override
	public Optional<Book> findByISBN(String isbn) {
		return bookRepository.findByIsbn(isbn);
	}

	@Override
	public Book save(Book dto) {
		return bookRepository.save(dto);
	}

	@Override
	public Book update(Book dto) {
		Book saved = bookRepository.findById(dto.getId()).orElseThrow();
		saved.setAuthorId(dto.getAuthorId());
		saved.setIsbn(dto.getIsbn());
		saved.setPublisher(dto.getPublisher());
		saved.setTitle(dto.getTitle());
		return bookRepository.saveAndFlush(saved);
	}

	@Override
	public void deleteById(Long id) {
		bookRepository.deleteById(id);
	}

}
