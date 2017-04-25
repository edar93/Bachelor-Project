package vsb.cec0094.bachelorProject.resource;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.AdministrationUser;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/useradministration")
@EnableAspectJAutoProxy
@CrossOrigin
public class AdministrationResource {

    private final static int pageSize = 10;

    private final static String RESET_PASSWORD = "RESET_PASSWORD";
    private final static String GRANT_ADMIN_ROLE = "GRANT_ADMIN_ROLE";
    private final static String UNLOCKED = "LOCK1";
    private final static String LOCKED = "LOCK0";

    @Inject
    AccountDao accountDao;

    @GET
    @Path("{page}")
    public Response getAllUsers(@PathParam("page") Integer page) {
        page = page - 1;
        List<AdministrationUser> administrationUserList = accountDao.getAllUsers(page, pageSize);
        return Response.ok().entity(administrationUserList).build();
    }

    @GET
    @Path("/pagesCount")
    public Response getPagesCount(){
        int pagesCount = accountDao.getPagesCount(pageSize);
        return Response.ok().entity(pagesCount).build();
    }

    @PUT
    @Path("{login}/{action}")
    public Response userUpdate(@PathParam("login") String login, @PathParam("action") String action) {
        if (RESET_PASSWORD.equals(action)) {
            accountDao.resetPassword(login);
            return Response.ok().build();
        }
        if (GRANT_ADMIN_ROLE.equals(action)) {
            accountDao.grantRoleToUser(login, "ROLE_ADMINISTRATOR");
            return Response.ok().build();
        }
        if (LOCKED.equals(action)) {
            accountDao.setUserLock(login, true);
            return Response.ok().build();
        }
        if (UNLOCKED.equals(action)) {
            accountDao.setUserLock(login, false);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("{login}")
    public Response deleteUser(@PathParam("login") String login) {
        accountDao.deteleUser(login);
        return Response.ok().build();
    }
}
