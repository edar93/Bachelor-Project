package vsb.cec0094.bachelorProject.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vsb.cec0094.bachelorProject.dao.GamesHolder;
import vsb.cec0094.bachelorProject.exceptions.GameDoesNotExist;
import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.exceptions.NotPlayersTurnException;
import vsb.cec0094.bachelorProject.exceptions.TooExpensiveExpeditionException;
import vsb.cec0094.bachelorProject.gameLogic.GameManipulator;
import vsb.cec0094.bachelorProject.service.UsersProvider;

@Controller
@RequestMapping("/play")
@EnableAspectJAutoProxy
public class PlayGameResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayGameResource.class);
    @Autowired
    private GamesHolder gamesHolder;
    @Autowired
    private UsersProvider usersProvider;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/startGame")
    public ResponseEntity<Void> createGameInQueue() throws CloneNotSupportedException {
        LOGGER.debug("createGameInQueue was called");
        if (usersProvider.getLogin().equals(usersProvider.getGameInQueue().getOwner())) {
            gamesHolder.addGame(new GameManipulator(usersProvider.getGameInQueue()));
        }
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/getMyGame")
    public ResponseEntity<GameManipulator> getMyGame() {
        LOGGER.debug("getMyGame was called");
        return ResponseEntity.ok().body(usersProvider.getGameManipulator());
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/facecard")
    public ResponseEntity<Void> faceCard() throws CloneNotSupportedException, GameDoesNotExist, NotPlayersTurnException, InvalidActionException {
        LOGGER.debug("faceCard was called");
        usersProvider.getGameManipulatorWhenIsPlayerOnTurn().faceCard();
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/pickcard")
    public ResponseEntity<Void> pickCard(@RequestBody Integer id) throws CloneNotSupportedException, GameDoesNotExist, NotPlayersTurnException {
        LOGGER.debug("pickCard was called");
        usersProvider.getGameManipulatorWhenIsPlayerOnTurn().playerGetCardFromTable(id);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/pickexpedition")
    public ResponseEntity<Void> pickExpedition(@RequestBody Integer id) throws CloneNotSupportedException, TooExpensiveExpeditionException, GameDoesNotExist, NotPlayersTurnException {
        LOGGER.debug("pickExpedition was called");
        usersProvider.getGameManipulatorWhenIsPlayerOnTurn().playerPickExpedition(id);
        return ResponseEntity.ok().build();
    }

}