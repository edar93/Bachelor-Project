package vsb.cec0094.bachelorProject.resource;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.repository.StatsRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/stats")
@EnableAspectJAutoProxy
public class StatsResource {

    @Inject
    private StatsRepository statsRepository;
    @Inject
    private AccountDao accountDao;

    @GET
    @Path("/{login}/{page}")
    public Response getPlayersGames(@PathParam("login") String login, @PathParam("page") Integer page) {
        if (accountDao.getUserByLogin(login) != null) {
            return Response.ok().entity(statsRepository.getPlayersStats(login, page)).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/pagesCount/{login}")
    public Response getPagesCount(@PathParam("login") String login) {
        return Response.ok().entity(statsRepository.getPagesCount(login)).build();
    }

    @GET
    @Path("/game/{gameId}")
    public Response getGame(@PathParam("gameId") Long gameId) {
        return Response.ok().entity(statsRepository.getGame(gameId)).build();
    }
}
