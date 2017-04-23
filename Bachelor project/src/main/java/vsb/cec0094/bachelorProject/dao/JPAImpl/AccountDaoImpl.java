package vsb.cec0094.bachelorProject.dao.JPAImpl;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.AdministrationUser;
import vsb.cec0094.bachelorProject.models.User;
import vsb.cec0094.bachelorProject.models.UserRegistration;
import vsb.cec0094.bachelorProject.models.UserRole;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class AccountDaoImpl implements AccountDao {

    private static final String DEFAULT_PASSWIRD = "heslo1";

    private static final String GET_HIGHEST_ROLE_ID = "SELECT MAX(id) FROM UserRole";
    private static final String SELECT_ALL_ADMINISTRATIVE_USERS = "SELECT au FROM AdministrationUser au ORDER BY au.login ASC ";
    private static final String PLAYERS_COUNT = "SELECT count(au) FROM AdministrationUser au";
    private static final String IS_ADMIN = "SELECT au FROM AdministrationUser au WHERE au.login = :login";
    private static final String RESET_PASSWORD = "UPDATE UserRegistration ur set ur.password = :newPassword WHERE ur.login = :login";
    private static final String SET_LOCK = "UPDATE UserRegistration ur set ur.enabled = :lock WHERE ur.login = :login";
    private static final String DELETE_ROLES_NATIVE = "DELETE USER_ROLE where login = :login";
    private static final String DELETE_USER_NATIVE = "DELETE PLAYER where login = :login";

    @PersistenceContext
    private EntityManager em;

    private int getIdForNewRole() {
        try {
            return ((Integer) em.createQuery(GET_HIGHEST_ROLE_ID)
//                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .getSingleResult()
            )
                    + 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public void createUser(UserRegistration userRegistration) {
        em.persist(userRegistration);
    }

    @Override
    public void grantRoleToUser(String login, String role) {
        AdministrationUser administrationUser = em.find(AdministrationUser.class, login);

        UserRole userRole = new UserRole();
        userRole.setId(getIdForNewRole());
        userRole.setRole(role);
        em.persist(userRole);

//        administrationUser.setUserRoleList(new ArrayList<>());
        administrationUser.getUserRoleList().add(userRole);
    }

    @Override
    public List<AdministrationUser> getAllUsers(Integer page, Integer resultsCount) {
        return em.createQuery(SELECT_ALL_ADMINISTRATIVE_USERS, AdministrationUser.class)
                .setFirstResult(page * resultsCount)
                .setMaxResults(resultsCount)
                .getResultList();
    }

    @Override
    public Integer getPagesCount(Integer pageSize) {
        int plus = 0;
        long resultsConut = em.createQuery(PLAYERS_COUNT, Long.class)
                .getSingleResult();
        if (resultsConut % pageSize != 0) {
            plus = 1;
        }
        return (((int) resultsConut) / pageSize) + plus;
    }

    @Override
    public Boolean isAdmin(String player) throws NoResultException {
        AdministrationUser user = em.createQuery(IS_ADMIN, AdministrationUser.class)
                .setParameter("login", player)
                .getSingleResult();
        for (UserRole role : user.getUserRoleList()) {
            if ("ROLE_ADMINISTRATOR".equals(role.getRole())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void resetPassword(String login) {
        String newPassword = BCrypt.hashpw(DEFAULT_PASSWIRD, BCrypt.gensalt(12));
        em.createQuery(RESET_PASSWORD)
                .setParameter("login", login)
                .setParameter("newPassword", newPassword)
                .executeUpdate();
    }

    @Override
    public void setUserLock(String login, Boolean locked) {
        int lock;
        if (locked) {
            lock = 0;
        } else {
            lock = 1;
        }
        em.createQuery(SET_LOCK)
                .setParameter("login", login)
                .setParameter("lock", lock)
                .executeUpdate();
    }

    @Override
    public void deteleUser(String login) {
        em.createNativeQuery(DELETE_ROLES_NATIVE)
                .setParameter("login", login)
                .executeUpdate();
        em.createNativeQuery(DELETE_USER_NATIVE)
                .setParameter("login", login)
                .executeUpdate();
    }

    @Override
    public User getUserByLogin(String login) {
        return em.find(User.class, login);
    }

    @Override
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
