package vsb.cec0094.bachelorProject.resource;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.AdministrationUser;
import vsb.cec0094.bachelorProject.models.Message;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/useradministration")
public class AdministrationResource {

    private final static int pageSize = 3;

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
}
