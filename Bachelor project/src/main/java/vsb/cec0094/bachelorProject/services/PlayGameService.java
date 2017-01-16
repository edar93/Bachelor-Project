package vsb.cec0094.bachelorProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.dao.GamesHolder;
import vsb.cec0094.bachelorProject.exceptions.GameDoesNotExist;
import vsb.cec0094.bachelorProject.exceptions.NotPlayersTurnException;
import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.gameLogic.Player;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.util.List;

@Controller
@RequestMapping("/play")
public class PlayGameService {

    @Autowired
    private GameDao gameDao;
    @Autowired
    private GamesHolder gamesHolder;

    @MessageMapping("/sendAction/{owner}")
    @SendTo("/myGame/{owner}")
    public Game updateGame(@DestinationVariable String owner) {
        Game game = getValidatedGame(owner);
        return game;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/startGame")
    @ResponseBody
    public void createGameInQueue() {
        String player = SecurityContextHolder.getContext().getAuthentication().getName();
        GameInQueue game = gameDao.getPlayersGame(player);
        if (player.equals(game.getOwner())) {
            gamesHolder.addGame(new Game(game));
        }
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/getMyGame")
    @ResponseBody
    public Game getMyGame() {
        String player = SecurityContextHolder.getContext().getAuthentication().getName();
        GameInQueue game = gameDao.getPlayersGame(player);
        return gamesHolder.getGame(game.getOwner());
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/facecard")
    @ResponseBody
    public void faceCard() {
        String player = SecurityContextHolder.getContext().getAuthentication().getName();
        Game game = getValidatedGame(player);
        game.faceCard();
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/pickcard")
    @ResponseBody
    public void pickCard(@RequestBody Integer id) {
        String player = SecurityContextHolder.getContext().getAuthentication().getName();
        Game game = getValidatedGame(player);
        game.playerGetCardFromTable(id);
    }

    /**
     * Check if player is on turn
     *
     * @param localPlayer
     * @return game in which player is
     */
    private Game getValidatedGame(String localPlayer) {
        GameInQueue gameInQueue = gameDao.getPlayersGame(localPlayer);
        if (gameInQueue == null) {
            throw new GameDoesNotExist(" game for player: \"" + localPlayer + "\" goes not exist in database");
        }
        Game game = gamesHolder.getGame(gameInQueue.getOwner());
        if (game == null) {
            throw new GameDoesNotExist(" game for player: \"" + localPlayer + "\" goes not exist in memory");
        }
        List<Player> playerList = game.getPlayers();
        String playerOnTurn = playerList.get(game.getPlayerOnTurn()).getLogin();

        if (!playerOnTurn.equals(localPlayer)) {
            throw new NotPlayersTurnException(" not \"" + localPlayer + "\" turn");
        }
        return game;
    }

}
