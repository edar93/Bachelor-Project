package vsb.cec0094.bachelorProject.resource;

import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.AdministrationUser;

import java.util.List;

@RestController
@RequestMapping("/administration")
public class AdministrationResource {

    @Autowired
    AccountDao accountDao;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public Response getAllUsers() {

        List<AdministrationUser> administrationUserList = accountDao.getAllUsers();
        System.out.println("output <>>>><>><><><");
        System.out.println(administrationUserList.get(0));
        System.out.println("output <>>>><>><><><");
        return Response.ok().entity(administrationUserList).build();
    }

//    @RequestMapping(method = RequestMethod.GET, value = "/test")
//    public List<AdministrationUser> getAllUsers() {
//
//        List<AdministrationUser> administrationUserList = accountDao.getAllUsers();
//        System.out.println("output <>>>><>><><><");
//        System.out.println(administrationUserList.get(0));
//        System.out.println("output <>>>><>><><><");
//        return administrationUserList;
//    }
}
