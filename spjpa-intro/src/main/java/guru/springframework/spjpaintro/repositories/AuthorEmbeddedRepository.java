package guru.springframework.spjpaintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.spjpaintro.domain.AuthorEmbedded;

public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, AuthorEmbedded.AuthorEmbeddedId> {
	// Empty
}
