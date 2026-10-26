package guru.springframework.sdjpahibernatedao.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Book.findAll", query = "FROM Book")
@NamedQuery(name = "Book.findByTitle", query = "FROM Book WHERE title = :title")
@NamedQuery(name = "Book.findByIsbn", query = "FROM Book WHERE isbn LIKE :isbn")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String isbn;
	private String publisher;
	private Long authorId;

}
