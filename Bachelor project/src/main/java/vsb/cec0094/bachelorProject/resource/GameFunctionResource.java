package vsb.cec0094.bachelorProject.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.exceptions.NoEmptyPlaceInGame;
import vsb.cec0094.bachelorProject.models.GameInQueue;
import vsb.cec0094.bachelorProject.service.UsersProvider;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameFunctionResource {

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
    public ResponseEntity<Void> joinGame(@RequestBody String owner) {
        try {
            gameDao.joinGame(owner, usersProvider.getLogin());
        } catch (NoEmptyPlaceInGame noEmptyPlaceInGame) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getplayersgame")
    public ResponseEntity<GameInQueue> getPlayersGame(@RequestBody String player) {
        return ResponseEntity.ok().body(gameDao.getPlayersGame(player));
    }

}
