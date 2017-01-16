package vsb.cec0094.bachelorProject.LoginHandlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SuccessLogout extends SimpleUrlLogoutSuccessHandler {

    @CrossOrigin
    @Override
    public void onLogoutSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
            throws IOException {
        System.out.println("Logout");
    }
}
