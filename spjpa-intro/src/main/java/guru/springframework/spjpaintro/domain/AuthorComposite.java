package guru.springframework.spjpaintro.domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(AuthorComposite.AuthorCompositeId.class)
public class AuthorComposite {

	@Id
	private String firstName;
	@Id
	private String lastName;
	private String country;

	public AuthorComposite(String firstName, String lastName, String country) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@EqualsAndHashCode
	public static class AuthorCompositeId implements Serializable {
		private String firstName;
		private String lastName;
	}
}
