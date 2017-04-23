package vsb.cec0094.bachelorProject.dao.JPAImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class IdGeneratorDao {

    private static final String GET_HIGHEST_STATS_RECORD_ID = "SELECT MAX(id) FROM StatsRecord";
    private static final String GET_HIGHEST_PLAYER_ID = "SELECT MAX(id) FROM Player";
    private static final String GET_HIGHEST_CARD_ID = "SELECT MAX(id) FROM Card";

    @PersistenceContext
    EntityManager em;

    public Long getIdForNewStatsRecord() {
        try {
            return ((Long) em.createQuery(GET_HIGHEST_STATS_RECORD_ID)
//                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .getSingleResult()
            )
                    + 1;
        } catch (NullPointerException e) {
            return 0L;
        }
    }

    public Long getIdForNewPlayer() {
        try {
            return ((Long) em.createQuery(GET_HIGHEST_PLAYER_ID)
//                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .getSingleResult()
            )
                    + 1L;
        } catch (NullPointerException e) {
            return 0L;
        }
    }

    public Integer getIdForNewCard() {
        try {
            return ((Integer) em.createQuery(GET_HIGHEST_CARD_ID)
//                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .getSingleResult()
            )
                    + 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
