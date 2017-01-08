package vsb.cec0094.bachelorProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.dao.GamesHolder;
import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.gameLogic.Player;
import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.card.CardType;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.util.ArrayList;
import java.util.List;

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
    public Game createTestGameInQueue(@RequestBody GameInQueue game) {
        Game activeGame = new Game(game);
        List cardList = new ArrayList<Card>();
        Player p = new Player("lojzik");

        cardList.add(new Card(CardType.SETTLER, 4, 1));
        p.setCards(cardList);

        List<Player> playerList = activeGame.getPlayers();

        playerList.set(1, p);
        Player activePlayer = playerList.get(0);
        activePlayer.setCards(cardList);

        Table table = new Table();
        table.setCards(cardList);
        activeGame.setTable(table);

        return activeGame;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/startGame")
    @ResponseBody
    public void createGameInQueue() {
        String player = SecurityContextHolder.getContext().getAuthentication().getName();
        GameInQueue game = gameDao.getPlayersGame(player);
        if (player.equals(game.getOwner())) {
            gamesHolder.addGame(new Game(game));
            System.out.println("game was added");
        }
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/getMyGame")
    @ResponseBody
    public Game getMyGame() {
        String player = SecurityContextHolder.getContext().getAuthentication().getName();
        GameInQueue game = gameDao.getPlayersGame(player);
        System.out.println("founded game " + game);
        return gamesHolder.getGame(game.getOwner());
    }

}
