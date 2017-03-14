package vsb.cec0094.bachelorProject.resource;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.AdministrationUser;
import vsb.cec0094.bachelorProject.models.Message;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/administration")
public class AdministrationResource {

    @Inject
    AccountDao accountDao;

    @GET
    @Path("/test")
    public Response getAllUsers() {

//        List<AdministrationUser> administrationUserList = accountDao.getAllUsers();
//        System.out.println("output 11111 <>>>><>><><><");
//        System.out.println(administrationUserList.get(0));
//        System.out.println("output 11111 <>>>><>><><><");
        System.out.println("just test ---++");
        Message message = new Message();
        message.setText("test string");
        return Response.ok().entity(message).build();
//        return "test2";
//        return Response.ok().entity(administrationUserList).build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/administration/test2")
    public List<AdministrationUser> getAllUsers2() {

        List<AdministrationUser> administrationUserList = accountDao.getAllUsers();
        System.out.println("output 2222222 <>>>><>><><><");
        System.out.println(administrationUserList.get(0));
        System.out.println("output 2222222 <>>>><>><><><");
        return administrationUserList;
    }
}
