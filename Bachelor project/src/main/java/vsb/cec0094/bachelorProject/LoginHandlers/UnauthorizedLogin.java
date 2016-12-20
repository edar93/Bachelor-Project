package vsb.cec0094.bachelorProject.LoginHandlers;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UnauthorizedLogin implements AuthenticationFailureHandler {

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
	public void onAuthenticationFailure(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2)
			throws IOException, ServletException {
		System.out.println("fail");
		System.out.println(arg0.getParameter("username"));
		System.out.println(arg0.getParameter("password"));
		sendResponse(arg1, 403, arg0.getParameter("username"));

	}

}
