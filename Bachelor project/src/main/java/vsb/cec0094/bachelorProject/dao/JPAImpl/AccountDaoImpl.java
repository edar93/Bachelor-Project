package vsb.cec0094.bachelorProject.dao.JPAImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.UserRegistration;
import vsb.cec0094.bachelorProject.models.UserRole;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class AccountDaoImpl implements AccountDao {

    private static final String GET_HIGHEST_ROLE_ID = "SELECT MAX(id) FROM UserRole";

    @PersistenceContext
    private EntityManager em;

    private int getIdForNewRole() {
        try {
            return ((Integer) em.createQuery(GET_HIGHEST_ROLE_ID)
                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .getSingleResult()
            )
                    + 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public void createUser(UserRegistration userRegistration) {
        UserRole userRole = new UserRole();
        userRole.setUser(userRegistration.getLogin());
        userRole.setRole("ROLE_USER");
        userRole.setId(getIdForNewRole());
        em.persist(userRole);
        em.persist(userRegistration);
    }
}
