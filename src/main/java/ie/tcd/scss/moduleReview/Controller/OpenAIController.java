package ie.tcd.scss.moduleReview.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ie.tcd.scss.moduleReview.service.OpenAIService;

@RestController
@RequestMapping("/api/chat")
public class OpenAIController {

    private final OpenAIService openAIService;

    @Autowired
    public OpenAIController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/conversations")
    public String createConversation() {
        return openAIService.createConversation();
    }

    @PostMapping("/message")
    public String chat(@RequestParam String conversationId, @RequestBody String message) {
        return openAIService.chat(conversationId, message);
    }
}
