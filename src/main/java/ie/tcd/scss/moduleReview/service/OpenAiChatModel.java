package ie.tcd.scss.moduleReview.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ie.tcd.scss.moduleReview.Config.OpenAIconfig;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OpenAiChatModel {

    private final OpenAIconfig openAIConfig;
    private final RestTemplate restTemplate;

    public OpenAiChatModel(OpenAIconfig openAIConfig, RestTemplate restTemplate) {
        this.openAIConfig = openAIConfig;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void logConfig() {
        log.info("Botpress API URL: {}", openAIConfig.getApiUrl());
        log.info("Webhook ID: {}", openAIConfig.getWebhookId());
        log.info("User Key: {}", (openAIConfig.getUserKey() != null && !openAIConfig.getUserKey().isEmpty() ? "Loaded" : "Missing"));
    }

    /**
     * Creates a new conversation via the Botpress.
     *
     * @return The conversation response containing the conversation ID.
     */
    public ConversationResponse createConversation() {
        String url = String.format("%s/%s/conversations", openAIConfig.getApiUrl(), openAIConfig.getWebhookId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-user-key", openAIConfig.getUserKey());

        log.info("Sending request to create a conversation at URL: {}", url);
        log.info("Headers: {}", headers);

        String requestBody = "{}";  

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<ConversationResponse> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, ConversationResponse.class);
            log.debug("Received response: {}", response); // Detailed response log
        } catch (Exception e) {
            log.error("Error creating conversation: {}", e.getMessage(), e);
            throw new RuntimeException("Error while creating conversation", e);
        }

        if (response.getBody() == null) {
            log.error("Failed to create conversation: Empty response body");
            throw new RuntimeException("Failed to create conversation: Empty response body");
        }

        // Log the conversation ID 
        ConversationResponse.Conversation conversation = response.getBody().getConversation();
        if (conversation == null || conversation.getId() == null) {
            log.error("Failed to create conversation: No conversation ID returned");
            throw new RuntimeException("Failed to create conversation: No conversation ID returned");
        }

        log.info("Conversation created successfully with ID: {}", conversation.getId());
        return response.getBody();
    }

    /**
     * Sends a message in a conversation.
     *
     * @param conversationId The conversation ID.
     * @param message        The message to send.
     * @return The message response.
     */
    public MessageResponse sendMessage(String conversationId, String message) {
        String url = String.format("%s/%s/messages", openAIConfig.getApiUrl(), openAIConfig.getWebhookId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-user-key", openAIConfig.getUserKey());

        log.info("Sending message to conversation ID: {}", conversationId);
        log.info("Headers: {}", headers);

        String requestBody = String.format("""
        {
            "conversationId": "%s",
            "payload": {
                "type": "text",
                "text": "%s"
            }
        }
        """, conversationId, escapeJson(message));

        log.info("Request Body: {}", requestBody);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<MessageResponse> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, MessageResponse.class);
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage(), e);
            throw new RuntimeException("Error while sending message", e);
        }

        if (response.getBody() == null || response.getBody().getMessage() == null) {
            log.error("Failed to send message: Empty response");
            throw new RuntimeException("Failed to send message: Empty response");
        }

        log.info("Message sent successfully with response: {}", response.getBody().getMessage().getPayload().getText());
        return response.getBody();
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public static class ConversationResponse {
        private Conversation conversation;

        public Conversation getConversation() {
            return conversation;
        }

        public void setConversation(Conversation conversation) {
            this.conversation = conversation;
        }

        public static class Conversation {
            private String id;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }

    public static class MessageResponse {
        private Message message;

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

        public static class Message {
            private Payload payload;

            public Payload getPayload() {
                return payload;
            }

            public void setPayload(Payload payload) {
                this.payload = payload;
            }

            public static class Payload {
                private String text;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }
            }
        }
    }
}
