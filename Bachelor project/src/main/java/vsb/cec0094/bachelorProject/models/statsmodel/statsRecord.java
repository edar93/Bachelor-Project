package vsb.cec0094.bachelorProject.models.statsmodel;

import org.hibernate.annotations.Type;
import vsb.cec0094.bachelorProject.gameLogic.Player;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "stats_record")
public class statsRecord {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "Game_date")
    @Type(type = "timestamp")
    private Date createDate;

    // , referencedColumnName = "id"
    @OneToMany
    @JoinColumn(name = "stats_record")
    private List<Player> playerList;
}
