package vsb.cec0094.bachelorProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.User;
import vsb.cec0094.bachelorProject.models.UserRegistration;

/**
 * Created by User on 19. 12. 2016.
 */

@Controller
@RequestMapping("/accounts")
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    @RequestMapping(method = RequestMethod.POST, value="/register")
    @ResponseBody
    public void register(@RequestBody UserRegistration userRegistration){
        userRegistration.setEnabled(1);
        accountDao.createUser(userRegistration);
    };

    @RequestMapping(method = RequestMethod.GET, value="/getLoggedUserLogin")
    @ResponseBody
    public String getLogin(){
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return user;
    };

}
