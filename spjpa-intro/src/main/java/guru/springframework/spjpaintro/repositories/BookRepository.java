package guru.springframework.spjpaintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.spjpaintro.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
