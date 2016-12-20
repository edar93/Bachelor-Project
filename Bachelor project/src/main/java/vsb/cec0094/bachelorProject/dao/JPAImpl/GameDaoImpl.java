package vsb.cec0094.bachelorProject.dao.JPAImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class GameDaoImpl implements GameDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createGameInQueue(GameInQueue gameInQueue) {
        em.persist(gameInQueue);
    }
}
