package ie.tcd.scss.moduleReview.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class OpenAIconfig {

    @Value("${botpress.api.url}")
    private String apiUrl;

    @Value("${botpress.webhook.id}")
    private String webhookId;

    @Value("${botpress.user.key}")
    private String userKey;

    /**
     * Creates a RestTemplate bean with an interceptor for adding the user key to the x-user-key header.
     *
     * @return a RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("x-user-key", userKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }

    /**
     * Logs configuration values at startup.
     */
    @PostConstruct
    public void logConfig() {
        log.info("Botpress API URL: {}", apiUrl);
        log.info("Webhook ID: {}", webhookId);
        log.info("User Key: {}", (userKey != null && !userKey.isEmpty() ? "Loaded" : "Missing"));
    }

    // Getters for shared values
    public String getApiUrl() {
        return apiUrl;
    }

    public String getWebhookId() {
        return webhookId;
    }

    public String getUserKey() {
        return userKey;
    }
}
