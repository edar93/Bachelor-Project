package vsb.cec0094.bachelorProject.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.UserRegistration;

@RestController
@RequestMapping("/accounts")
public class AccountResource {

    @Autowired
    private AccountDao accountDao;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<Void> register(@RequestBody UserRegistration userRegistration) {
        userRegistration.setEnabled(1);
        userRegistration.setPassword(BCrypt.hashpw(userRegistration.getPassword(), BCrypt.gensalt(12)));
        accountDao.createUser(userRegistration);
        accountDao.grantRoleToUser(userRegistration.getLogin(), "ROLE_USER");
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getLoggedUserLogin")
    public ResponseEntity<String> getLogin() {
        System.out.println("llllll:::::::?<<<");
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok().body(user);
    }

}
