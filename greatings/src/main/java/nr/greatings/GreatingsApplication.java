package nr.greatings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class GreatingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreatingsApplication.class, args);
	}

	private int port;

	@EventListener
	public void ready(WebServerInitializedEvent ev) {
		this.port = ev.getWebServer().getPort();
	}

	@GetMapping("/hello/{name}")
	String greet(@PathVariable String name) {
		return """
					Hello, %s from localhost:%s!
				""".formatted(name, port);
	}

}
