package vsb.cec0094.bachelorProject.models.statsModel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;
import vsb.cec0094.bachelorProject.gameLogic.Player;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "game_record")
public class StatsRecord {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "Game_date")
    @Type(type = "timestamp")
    private Date createDate;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "record_id")
    private Set<Player> playerList;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StatsRecord that = (StatsRecord) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(createDate, that.createDate)
                .append(playerList, that.playerList)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(createDate)
                .append(playerList)
                .toHashCode();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(Set<Player> playerList) {
        this.playerList = playerList;
    }
}
