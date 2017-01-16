package vsb.cec0094.bachelorProject.LoginHandlers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Transactional
public class SuccessLogin extends SavedRequestAwareAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    public static void sendResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, origin, authorization, accept, client-security-token");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(message);
        response.setStatus(status);
        writer.flush();
        writer.close();
    }

    @CrossOrigin
    @Override
    public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
            throws IOException, ServletException {
        System.out.println("succ");
        System.out.println(arg0.getParameter("password"));
        System.out.println(arg0.getParameter("username"));
        sendResponse(arg1, 200, arg0.getParameter("username"));
    }


}

