package au.com.mylibrary.bookcatalogue.bookcatalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * BookCatalogue main class to bootstrap the application
 */
@PropertySource("classpath:messages.properties")
@SpringBootApplication
public class BookCatalogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookCatalogueApplication.class, args);
	}

}
