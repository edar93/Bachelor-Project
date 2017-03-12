package vsb.cec0094.bachelorProject.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.exceptions.NoEmptyPlaceInGame;
import vsb.cec0094.bachelorProject.models.GameInQueue;
import vsb.cec0094.bachelorProject.service.UsersProvider;

import java.util.List;

@RestController
@RequestMapping("/game")
@EnableAspectJAutoProxy
@Scope("request")
public class GameFunctionResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameFunctionResource.class);

    @Autowired
    private GameDao gameDao;
    @Autowired
    private UsersProvider usersProvider;

    @RequestMapping(method = RequestMethod.POST, value = "/cratenewgame")
    public ResponseEntity<Void> createGameInQueue() {
        GameInQueue gameInQueue = new GameInQueue();
        gameInQueue.setMaxPlayersCount(3);
        gameInQueue.setOwner(usersProvider.getLogin());

        gameDao.createGameInQueue(gameInQueue);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/basegamestatus")
    public ResponseEntity<GameInQueue> getBaseGameStatus() {
        return ResponseEntity.ok().body(gameDao.getGameInQueue(usersProvider.getLogin()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getallgamesinqueue")
    @ResponseBody
    public ResponseEntity<List> getAllGames() {
        return ResponseEntity.ok().body(gameDao.getAllGames());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/joingame")
    public ResponseEntity<Void> joinGame(@RequestBody Integer id) {
        try {
            gameDao.joinGame(id, usersProvider.getLogin());
        } catch (NoEmptyPlaceInGame noEmptyPlaceInGame) {
            LOGGER.debug(usersProvider.getLogin() + " tried to join full game:" + id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getplayersgame")
    public ResponseEntity<GameInQueue> getPlayersGame(@RequestBody String player) {
        return ResponseEntity.ok().body(gameDao.getPlayersGame(player));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/leftgame")
    public ResponseEntity<Void> leftGame() {
        gameDao.leftGame(usersProvider.getLogin(), usersProvider.getGameInQueue());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/kick")
    public ResponseEntity<Void> kickPlayer(@RequestBody String player) {
        gameDao.leftGame(player, gameDao.getPlayersGame(player));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/changeplayersmaxcount")
    public ResponseEntity<Void> changeMaxPlayersCount(@RequestBody Integer newCount) {
        if (usersProvider.getGameInQueue().getPlayersList().size() <= newCount){
            gameDao.setNewMaxPlayersCount(newCount, usersProvider.getGameInQueue());
        }
        return ResponseEntity.ok().build();
    }

}
