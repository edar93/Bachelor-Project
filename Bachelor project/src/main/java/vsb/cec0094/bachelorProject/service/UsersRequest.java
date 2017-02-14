package vsb.cec0094.bachelorProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.dao.GamesHolder;
import vsb.cec0094.bachelorProject.gameLogic.GameManipulator;
import vsb.cec0094.bachelorProject.models.GameInQueue;

@Service
public class UsersRequest {

    @Autowired
    private GameDao gameDao;
    @Autowired
    private GamesHolder gamesHolder;

    private String login;
    private GameInQueue gameInQueue;
    private GameManipulator gameManipulator;

    public void prepareUser(String login) {
        if (login != null) {
            gameInQueue = gameDao.getPlayersGame(login);
        }
        if (gameInQueue != null) {
            gameManipulator = gamesHolder.getGame(gameInQueue.getOwner());
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public GameInQueue getGameInQueue() {
        return gameInQueue;
    }

    public void setGameInQueue(GameInQueue gameInQueue) {
        this.gameInQueue = gameInQueue;
    }

    public GameManipulator getGameManipulator() {
        return gameManipulator;
    }

    public void setGameManipulator(GameManipulator gameManipulator) {
        this.gameManipulator = gameManipulator;
    }
}
