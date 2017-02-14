package vsb.cec0094.bachelorProject.resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import vsb.cec0094.bachelorProject.service.UsersRequest;

@Aspect
public class UserSaver {

    @Autowired
    UsersRequest usersRequest;

    //@Before("execution(* com.mkyong.customer.bo.CustomerBo.addCustomer(..))")
    @Before("execution (* vsb.cec0094.bachelorProject.resource.PlayGameResource.*(..))")
    public void setUser(JoinPoint joinPoint) {
        System.out.println("aspect was called --------------------------------------------------");
        usersRequest.prepareUser();
    }
}
