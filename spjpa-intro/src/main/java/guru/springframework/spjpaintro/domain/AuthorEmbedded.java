package guru.springframework.spjpaintro.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "author_composite")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AuthorEmbedded {

	@EmbeddedId
	private AuthorEmbeddedId embeddedId;
	private String country;

	public AuthorEmbedded(AuthorEmbeddedId embeddedId, String country) {
		this.embeddedId = embeddedId;
		this.country = country;
	}

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@EqualsAndHashCode
	@Embeddable
	@Builder
	public static class AuthorEmbeddedId implements Serializable {
		private String firstName;
		private String lastName;
	}
}
