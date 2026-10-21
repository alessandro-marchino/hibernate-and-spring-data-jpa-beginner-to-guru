package guru.springframework.spjpaintro.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import guru.springframework.spjpaintro.domain.BookUuid;

public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {
	// Empty
}
