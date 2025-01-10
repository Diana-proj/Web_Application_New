package ie.tcd.scss.moduleReview.service;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OpenAIService {

    private final OpenAiChatModel chatModel;

    public OpenAIService(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String createConversation() {
        log.info("Creating a new conversation...");
        OpenAiChatModel.ConversationResponse response = chatModel.createConversation();
        String conversationId = response.getConversation().getId();
        log.info("Created conversation with ID: {}", conversationId);
        return conversationId;
    }

    public String chat(String conversationId, String message) {
        log.info("Sending message to conversation {}: {}", conversationId, message);
        OpenAiChatModel.MessageResponse response = chatModel.sendMessage(conversationId, message);
        String responseContent = response.getMessage().getPayload().getText();
        log.info("Received response: {}", responseContent);
        return responseContent;
    }
}
