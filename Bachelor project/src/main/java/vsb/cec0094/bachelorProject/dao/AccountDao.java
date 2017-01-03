package vsb.cec0094.bachelorProject.dao;

import vsb.cec0094.bachelorProject.models.User;
import vsb.cec0094.bachelorProject.models.UserRegistration;

public interface AccountDao {

    void createUser(UserRegistration userRegistration);
}
