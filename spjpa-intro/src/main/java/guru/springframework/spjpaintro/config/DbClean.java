package guru.springframework.spjpaintro.config;

import org.springframework.boot.flyway.autoconfigure.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("clean")
public class DbClean {

	@Bean
	FlywayMigrationStrategy clean() {
		return flyway -> {
			flyway.clean();
			flyway.migrate();
		};
	}
}
