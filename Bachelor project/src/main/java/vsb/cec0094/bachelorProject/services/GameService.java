package vsb.cec0094.bachelorProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.dao.TestDao;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.util.ArrayList;

@Controller
@RequestMapping("/game")
public class GameService {

    @Autowired
    private GameDao gameDao;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/cratenewgame")
    @ResponseBody
    public GameInQueue createGameInQueue() {
        System.out.println("method was called");
        GameInQueue gameInQueue = new GameInQueue();
        gameInQueue.setMaxPlayersCount(3);
        String owner = SecurityContextHolder.getContext().getAuthentication().getName();
        gameInQueue.setOwner(owner);
        System.out.println(owner);
        gameDao.createGameInQueue(gameInQueue);
        return gameInQueue;
    }

    ;
}
