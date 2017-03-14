package vsb.cec0094.bachelorProject.resource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.dao.GamesHolder;
import vsb.cec0094.bachelorProject.exceptions.GameDoesNotExist;
import vsb.cec0094.bachelorProject.exceptions.NotPlayersTurnException;
import vsb.cec0094.bachelorProject.gameLogic.GameManipulator;
import vsb.cec0094.bachelorProject.models.GameInQueue;
import vsb.cec0094.bachelorProject.models.Message;

import javax.inject.Inject;

@Controller
public class WebSockets {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSockets.class);

    @Inject
    private GameDao gameDao;
    @Inject
    private GamesHolder gamesHolder;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public Message receive(Message message) {
        return message;
    }

    @MessageMapping("/sendAction/{id}")
    @SendTo("/myGame/{id}")
    public GameManipulator updateGame(@DestinationVariable Integer id) throws CloneNotSupportedException, GameDoesNotExist, NotPlayersTurnException {
        LOGGER.debug("updateGame was called with id:" + id);
        GameInQueue gameInQueue = gameDao.getGameById(id);
        if (gameInQueue == null) {
            throw new GameDoesNotExist(" game for id: \"" + id + "\" does not exist in database");
        }
        return gamesHolder.getGame(gameInQueue.getId());
    }

}
