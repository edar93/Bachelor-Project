package vsb.cec0094.bachelorProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "player")
public class User {

    @Id
    @Column(name = "login")
    @Size(max = 100)
    private String login;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "game_id")
    private GameInQueue gameInQueue;

    @JsonIgnore
    @Column(name = "in_ended_game")
    private Integer inEndedGame;

    public Integer getInEndedGame() {
        return inEndedGame;
    }

    public void setInEndedGame(Integer inEndedGame) {
        this.inEndedGame = inEndedGame;
    }

    public GameInQueue getGameInQueue() {
        return gameInQueue;
    }

    public void setGameInQueue(GameInQueue gameInQueue) {
        this.gameInQueue = gameInQueue;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
