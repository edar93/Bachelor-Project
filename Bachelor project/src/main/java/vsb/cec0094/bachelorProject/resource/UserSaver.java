package vsb.cec0094.bachelorProject.resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import vsb.cec0094.bachelorProject.service.UsersRequest;

@Aspect
public class UserSaver {

    @Autowired
    UsersRequest usersRequest;

    @Before("execution (* vsb.cec0094.bachelorProject.resource.PlayGameResource.*(..))")
    public void setUser(JoinPoint joinPoint) {

        System.out.println(joinPoint.getSignature().getName() + "< joinPoint.getSignature().getName() ");
        System.out.println(joinPoint.getSignature().toString() + "< joinPoint.getSignature().toString()");
        for (Object o : joinPoint.getArgs()) {
            System.out.println(o.toString() + "< -----" + o);
        }

        System.out.println(RequestContextHolder.getRequestAttributes() + "< RequestContextHolder.getRequestAttributes()");
        System.out.println(SecurityContextHolder.getContext() + "< SecurityContextHolder.getContext()");

        // HttpServletRequest httpRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();


//        System.out.println( httpRequest.getUserPrincipal() + "< httpRequest.getUserPrincipal()");
//        System.out.println(httpRequest.getLocalName() + "< httpRequest.getLocalName()");
//        login = SecurityContextHolder.getContext().getAuthentication().getName();
        usersRequest.prepareUser("adam");
    }
}
