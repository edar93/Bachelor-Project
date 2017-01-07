package vsb.cec0094.bachelorProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.dao.GamesHolder;
import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.models.GameInQueue;

@Controller
@RequestMapping("/play")
public class PlayGameService {

    @Autowired
    private GameDao gameDao;
    @Autowired
    private GamesHolder gamesHolder;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/gettestgame")
    @ResponseBody
    public Game createGameInQueue(@RequestBody GameInQueue game) {
       // Game activeGame = new Game(game);
        return new Game(game);
    };
}
