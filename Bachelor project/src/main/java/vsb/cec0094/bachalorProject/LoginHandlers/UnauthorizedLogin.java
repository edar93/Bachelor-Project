package vsb.cec0094.bachalorProject.LoginHandlers;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthorizedLogin implements AuthenticationFailureHandler {

	@CrossOrigin
	@Override
	public void onAuthenticationFailure(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
			throws IOException, ServletException {
		System.out.println("fail");
		System.out.println(arg0.getParameter("username"));
		System.out.println(arg0.getParameter("password"));
//		arg1.sendRedirect(arg0.getContextPath() + "/fail");

	}

}
