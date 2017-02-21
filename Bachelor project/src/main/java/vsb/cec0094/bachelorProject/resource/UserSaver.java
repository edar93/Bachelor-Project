package vsb.cec0094.bachelorProject.resource;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import vsb.cec0094.bachelorProject.service.UsersProvider;

@Aspect
public class UserSaver {

    @Autowired
    private UsersProvider usersProvider;

    @Before("execution (* vsb.cec0094.bachelorProject.resource.PlayGameResource.*(..))," +
            "package vsb.cec0094.bachelorProject.resource.GameFunctionResource.*(..)")
    public void setUser() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        usersProvider.prepareUser(login);
    }
}
