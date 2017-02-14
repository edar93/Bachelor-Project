package vsb.cec0094.bachelorProject.resource;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vsb.cec0094.bachelorProject.models.Message;

@Controller
public class MessagesController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message receive(Message message) {
        return message;
    }
}
