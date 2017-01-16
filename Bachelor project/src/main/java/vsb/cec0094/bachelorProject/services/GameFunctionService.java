package vsb.cec0094.bachelorProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.util.List;

@Controller
@RequestMapping("/game")
public class GameFunctionService {

    @Autowired
    private GameDao gameDao;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/cratenewgame")
    @ResponseBody
    public GameInQueue createGameInQueue() {
        GameInQueue gameInQueue = new GameInQueue();
        gameInQueue.setMaxPlayersCount(3);
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        gameInQueue.setOwner(owner);

        gameDao.createGameInQueue(gameInQueue);
        return gameInQueue;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/basegamestatus")
    @ResponseBody
    public GameInQueue getBaseGameStatus() {
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        return gameDao.getGameInQueue(owner);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/getallgamesinqueue")
    @ResponseBody
    public List<GameInQueue> getAllGames() {
        return gameDao.getAllGames();
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.PUT, value = "/joingame")
    @ResponseBody
    public void joinGame(@RequestBody String owner) {
        String player = SecurityContextHolder.getContext().getAuthentication().getName();
        gameDao.joinGame(owner, player);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/getplayersgame")
    @ResponseBody
    public GameInQueue getPlayersGame(@RequestBody String player) {
        return gameDao.getPlayersGame(player);
    }

}
