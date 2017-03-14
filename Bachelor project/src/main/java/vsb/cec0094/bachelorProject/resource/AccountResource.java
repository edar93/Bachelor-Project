package vsb.cec0094.bachelorProject.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.UserRegistration;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RestController
//@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/accounts")
public class AccountResource {

    @Inject
    private AccountDao accountDao;

    @POST
    @Path("/register")
    //@QuerryParam
    public Response register(@RequestBody UserRegistration userRegistration) {
        userRegistration.setEnabled(1);
        userRegistration.setPassword(BCrypt.hashpw(userRegistration.getPassword(), BCrypt.gensalt(12)));
        accountDao.createUser(userRegistration);
        accountDao.grantRoleToUser(userRegistration.getLogin(), "ROLE_USER");
        return Response.ok().build();
    }

    @Path("/getLoggedUserLogin")
    @GET
    public Response getLogin() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        if("anonymousUser".equals(user)){
            return Response.status(401).entity(user).build();
        }
        return Response.ok().entity(user).build();
    }

}
