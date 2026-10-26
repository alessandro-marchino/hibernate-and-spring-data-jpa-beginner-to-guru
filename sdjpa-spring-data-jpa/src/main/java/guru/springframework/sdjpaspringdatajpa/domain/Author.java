package guru.springframework.sdjpaspringdatajpa.domain;

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
@NamedQuery(name = "Author.findAll", query = "FROM Author")
@NamedQuery(name = "Author.findByName", query = "FROM Author WHERE firstName = :firstName AND lastName = :lastName")
@NamedQuery(name = "Author.findByLastName", query = "FROM Author WHERE lastName LIKE :lastName")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String lastName;
}
