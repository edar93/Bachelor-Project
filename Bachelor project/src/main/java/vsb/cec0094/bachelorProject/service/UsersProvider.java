package vsb.cec0094.bachelorProject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.dao.GamesHolder;
import vsb.cec0094.bachelorProject.exceptions.NotPlayersTurnException;
import vsb.cec0094.bachelorProject.gameLogic.GameManipulator;
import vsb.cec0094.bachelorProject.models.GameInQueue;

@Component
//@Scope(value="request")//, proxyMode = ScopedProxyMode.TARGET_CLASS
public class UsersProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersProvider.class);

    @Autowired
    private GameDao gameDao;
    @Autowired
    private GamesHolder gamesHolder;
    private String login;
    private GameInQueue gameInQueue;
    private GameManipulator gameManipulator;

    public void prepareUser(String login) {
        LOGGER.debug(login + "< was set");
        this.login = login;
        if (login != null) {
            this.gameInQueue = gameDao.getPlayersGame(login);
        }
        if (gameInQueue == null) {
            this.gameManipulator = null;
        } else {
            this.gameManipulator = gamesHolder.getGame(gameInQueue.getId());
        }
    }

    public String getLogin() {
        return login;
    }

    public GameInQueue getGameInQueue() {
        return gameInQueue;
    }

    public GameManipulator getGameManipulator() {
        return gameManipulator;
    }

    public GameManipulator getGameManipulatorWhenIsPlayerOnTurn() throws NotPlayersTurnException {
        int activePlayerId = gameManipulator.getCurrentGame().getActivePlayer();
        String loginOfActivePlayer = gameManipulator.getCurrentGame().getPlayers().get(activePlayerId).getLogin();
        if (!this.login.equals(loginOfActivePlayer)) {
            LOGGER.debug("Inactive player{" + login + "} try to do move");
            throw new NotPlayersTurnException(" not \"" + login + "\" turn");
        }
        return gameManipulator;
    }
}
