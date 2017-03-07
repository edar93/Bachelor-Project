package vsb.cec0094.bachelorProject.resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vsb.cec0094.bachelorProject.service.UsersProvider;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Order(2)
public class UserSaver {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSaver.class);
    @Autowired
    private UsersProvider usersProvider;

    @Before("execution (* vsb.cec0094.bachelorProject.resource.*.*(..)),")
    public void setUser(JoinPoint joinPoint) {
//        HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String login = httpRequest.getUserPrincipal().getName();
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        LOGGER.debug("login from request is :" + login);
        //HttpSecurityUtils.getUsername(httpRequest);
        //String login = SecurityContextHolder.getContext().getAuthentication().getName();
        usersProvider.prepareUser(login);
    }

}
