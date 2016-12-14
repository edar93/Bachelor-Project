package vsb.cec0094.bachalorProject.LoginHandlers;

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
		//arg1.sendRedirect(arg0.getContextPath() + "/rest/logout?auth=" + arg2.getName());

	}
}
