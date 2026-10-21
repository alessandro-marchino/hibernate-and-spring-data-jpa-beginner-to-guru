package guru.springframework.spjpaintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.spjpaintro.domain.AuthorComposite;

public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, AuthorComposite.AuthorCompositeId> {
	// Empty
}
