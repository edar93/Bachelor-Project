package vsb.cec0094.bachelorProject.dao.JPAImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.User;
import vsb.cec0094.bachelorProject.models.UserRegistration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void createUser(UserRegistration userRegistration) {
        em.persist(userRegistration);
    }
}
