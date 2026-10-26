package guru.springframework.sdjpaspringdatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SdjpaSpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SdjpaSpringDataJpaApplication.class, args);
	}

}
