package guru.springframework.spjpaintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.spjpaintro.domain.BookNatural;

public interface BookNaturalRepository extends JpaRepository<BookNatural, String> {
	// Empty
}
