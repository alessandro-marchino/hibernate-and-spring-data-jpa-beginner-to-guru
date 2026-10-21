package guru.springframework.spjpaintro.domain;

import jakarta.persistence.Entity;
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
public class BookNatural {

	@Id
	private String title;
	private String isbn;
	private String publisher;

	public BookNatural(String title, String isbn, String publisher) {
		this.title = title;
		this.isbn = isbn;
		this.publisher = publisher;
	}

}
