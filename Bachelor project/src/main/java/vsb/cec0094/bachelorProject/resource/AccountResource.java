package vsb.cec0094.bachelorProject.resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.UserRegistration;
import vsb.cec0094.bachelorProject.service.UsersProvider;

import javax.inject.Inject;
import javax.persistence.NoResultException;
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
    UsersProvider usersProvider;
    @Inject
    private AccountDao accountDao;

    @POST
    @Path("/register")
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

    @GET
    @Path("/isAdmin")
    public Response isAdmin() {
        Boolean hasRole;
        try {
            hasRole = accountDao.isAdmin(usersProvider.getLogin());
        } catch (NoResultException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok().entity(hasRole).build();
    }

}
