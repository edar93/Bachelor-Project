package vsb.cec0094.bachelorProject.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "gameinqueue")
public class GameInQueue {

    @Id
    @Column(name = "owner")
    @Size(max = 100)
    private String owner;

    @OneToMany(mappedBy = "gameInQueue", fetch = FetchType.EAGER)
    private List<User> playersList;

    @Column(name = "maxPlayersCount")
    @Min(2)
    @Max(6)
    private int maxPlayersCount;

    public GameInQueue() {
    }

    @Override
    public String toString() {
        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
                .append("owner", owner)
                .append("playersList", playersList)
                .append("maxPlayersCount", maxPlayersCount)
                .toString();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<User> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<User> playersList) {
        this.playersList = playersList;
    }

    public int getMaxPlayersCount() {
        return maxPlayersCount;
    }

    public void setMaxPlayersCount(int maxPlayersCount) {
        this.maxPlayersCount = maxPlayersCount;
    }
}
