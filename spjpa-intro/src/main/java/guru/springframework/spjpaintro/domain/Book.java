package guru.springframework.spjpaintro.domain;

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
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	private String title;
	private String isbn;
	private String publisher;

	private Long authorId;

	public Book(String title, String isbn, String publisher, Long authorId) {
		this.title = title;
		this.isbn = isbn;
		this.publisher = publisher;
		this.authorId = authorId;
	}

}
