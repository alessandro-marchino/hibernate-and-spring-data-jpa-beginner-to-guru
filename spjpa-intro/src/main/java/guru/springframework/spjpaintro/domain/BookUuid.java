package guru.springframework.spjpaintro.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookUuid {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@EqualsAndHashCode.Include
	@Column(length = 16, columnDefinition = "binary(36)", updatable = false, nullable = false)
	private UUID id;

	private String title;
	private String isbn;
	private String publisher;

	public BookUuid(String title, String isbn, String publisher) {
		this.title = title;
		this.isbn = isbn;
		this.publisher = publisher;
	}

}
