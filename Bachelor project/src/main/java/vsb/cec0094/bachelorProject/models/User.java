package vsb.cec0094.bachelorProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "login")
    @Size(max = 100)
    private String login;

    @JsonIgnore
    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "gameinqueue")
    private GameInQueue gameInQueue;

    public User() {
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
