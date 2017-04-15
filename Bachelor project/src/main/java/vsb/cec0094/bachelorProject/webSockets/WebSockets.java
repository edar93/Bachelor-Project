package vsb.cec0094.bachelorProject.webSockets;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vsb.cec0094.bachelorProject.models.Message;

@Controller
public class WebSockets {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSockets.class);

    @MessageMapping("/sendMessage")
    @SendTo("/messages")
    public Message receive(Message message) {
        return message;
    }

    @MessageMapping("/sendAction/{id}")
    @SendTo("/myGame/{id}")
    public Boolean updateGame(@DestinationVariable Integer id) {
        LOGGER.debug("updateGame was called with id={}:", id);
        return true;
    }

}
