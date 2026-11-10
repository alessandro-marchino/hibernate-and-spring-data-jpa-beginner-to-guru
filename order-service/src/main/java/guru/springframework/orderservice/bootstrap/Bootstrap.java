package guru.springframework.orderservice.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		log.info("I was called!");
	}
}
