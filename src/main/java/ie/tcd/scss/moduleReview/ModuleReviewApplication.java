package ie.tcd.scss.moduleReview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Override default config for bean
@SpringBootApplication(exclude = org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration.class)
public class ModuleReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModuleReviewApplication.class, args);
	}

}
