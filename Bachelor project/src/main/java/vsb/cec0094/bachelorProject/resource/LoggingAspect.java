package vsb.cec0094.bachelorProject.resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;

@Aspect
@Order(1)
public class LoggingAspect {

    @Before("execution (* vsb.cec0094.bachelorProject.resource.*.*(..))" +
            "&& !execution(* vsb.cec0094.bachelorProject.webSockets.WebSockets.*(..))")
    public void logAction(JoinPoint joinPoint) {
        final Logger LOGGER = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringType());
        String logMessage = "LOGGED BY ASPECT - Method: \"" + joinPoint.getSignature().getName();
        if (joinPoint.getArgs().length == 0) {
            logMessage += "\" was called without args ";
        } else {
            logMessage += "\" was called with args: ";
            for (Object o : joinPoint.getArgs()) {
                if (o == null) {
                    logMessage += "\"" + "null" + "\" ";
                } else {
                    logMessage += "\"" + (o.toString()) + "\" ";
                }
            }
        }
        logMessage += "by :\"" + SecurityContextHolder.getContext().getAuthentication().getName() + "\"";
        LOGGER.debug(logMessage);
    }
}
