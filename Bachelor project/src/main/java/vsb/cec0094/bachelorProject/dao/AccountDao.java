package vsb.cec0094.bachelorProject.dao;

import vsb.cec0094.bachelorProject.models.AdministrationUser;
import vsb.cec0094.bachelorProject.models.User;
import vsb.cec0094.bachelorProject.models.UserRegistration;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public interface AccountDao {

    void createUser(UserRegistration userRegistration);

    void grantRoleToUser(String login, String role);

    List<AdministrationUser> getAllUsers(Integer page, Integer resultsCount);

    Integer getPagesCount(Integer pageSize);

    Boolean isAdmin(String player) throws NoResultException;

    void resetPassword(String login);

    void setUserLock(String login, Boolean locked);

    void deteleUser(String login);

    User getUserByLogin(String login);

    void setEm(EntityManager em);
}
