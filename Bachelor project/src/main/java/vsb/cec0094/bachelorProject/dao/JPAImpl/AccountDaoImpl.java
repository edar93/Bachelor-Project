package vsb.cec0094.bachelorProject.dao.JPAImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by User on 19. 12. 2016.
 */
@Repository
@Transactional
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void createUser(User user) {
        em.persist(user);
    }
}
