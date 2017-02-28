package vsb.cec0094.bachelorProject.resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import vsb.cec0094.bachelorProject.service.UsersProvider;

@Aspect
@Order(2)
public class UserSaver {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSaver.class);
    @Autowired
    private UsersProvider usersProvider;

    @Before("execution (* vsb.cec0094.bachelorProject.resource.PlayGameResource.*(..))," +
            "vsb.cec0094.bachelorProject.resource.GameFunctionResource.*(..)")
    public void setUser(JoinPoint joinPoint) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        usersProvider.prepareUser(login);
    }

}
