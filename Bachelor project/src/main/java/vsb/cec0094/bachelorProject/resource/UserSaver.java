package vsb.cec0094.bachelorProject.resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import vsb.cec0094.bachelorProject.service.UsersProvider;

import javax.inject.Inject;

@Aspect
@Order(2)
public class UserSaver {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSaver.class);
    @Inject
    private UsersProvider usersProvider;

    @Before("execution (* vsb.cec0094.bachelorProject.resource.*.*(..))" +
            "&& !execution(* vsb.cec0094.bachelorProject.webSockets.WebSockets.*(..))")
    public void setUser(JoinPoint joinPoint) {
//        HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String login = httpRequest.getUserPrincipal().getName();
        String login = null;

//        if(SecurityContextHolder.getContext() == null){
//            LOGGER.debug("SecurityContextHolder.getContext() is null");
//        }
//
//        if(SecurityContextHolder.getContext().getAuthentication() == null){
//            LOGGER.debug("SecurityContextHolder.getContext().getAuthentication() is null");
//        }
//
//        if(SecurityContextHolder.getContext().getAuthentication().getName() == null){
//            LOGGER.debug("SecurityContextHolder.getContext().getAuthentication().getName() is null");
//        }

        try {
            login = SecurityContextHolder.getContext().getAuthentication().getName();
            LOGGER.debug("login from request is :" + login);
            //HttpSecurityUtils.getUsername(httpRequest);
            //String login = SecurityContextHolder.getContext().getAuthentication().getName();
            usersProvider.prepareUser(login);
        }catch (Exception e){
            LOGGER.debug("Setting of to user provider fail");
            e.printStackTrace();
        }
    }

}
