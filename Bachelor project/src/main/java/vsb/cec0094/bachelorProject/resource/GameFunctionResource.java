package vsb.cec0094.bachelorProject.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameFunctionResource {

    @Autowired
    private GameDao gameDao;

    @RequestMapping(method = RequestMethod.POST, value = "/cratenewgame")
    public ResponseEntity<Void> createGameInQueue() {
        GameInQueue gameInQueue = new GameInQueue();
        gameInQueue.setMaxPlayersCount(3);
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        gameInQueue.setOwner(owner);

        gameDao.createGameInQueue(gameInQueue);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/basegamestatus")
    public ResponseEntity<GameInQueue> getBaseGameStatus() {
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok().body(gameDao.getGameInQueue(owner));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getallgamesinqueue")
    @ResponseBody
    public ResponseEntity<List> getAllGames() {
        return ResponseEntity.ok().body(gameDao.getAllGames());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/joingame")
    public ResponseEntity<Void> joinGame(@RequestBody String owner) {
        String player = SecurityContextHolder.getContext().getAuthentication().getName();
        gameDao.joinGame(owner, player);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/getplayersgame")
    public ResponseEntity<GameInQueue> getPlayersGame(@RequestBody String player) {
        return ResponseEntity.ok().body(gameDao.getPlayersGame(player));
    }

}
