package vsb.cec0094.bachelorProject.LoginHandlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessLogout extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
            throws IOException {
        SecurityContextHolder.clearContext();
        System.out.println("Logout");
    }
}
