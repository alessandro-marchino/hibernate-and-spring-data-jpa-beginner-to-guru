package guru.springframework.spjpaintro.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.spjpaintro.domain.AuthorUuid;

public interface AuthorUuidRepository extends JpaRepository<AuthorUuid, UUID> {
	// Empty
}
