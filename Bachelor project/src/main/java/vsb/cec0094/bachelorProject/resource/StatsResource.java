package vsb.cec0094.bachelorProject.resource;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
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
    StatsRepository statsRepository;

    @GET
    @Path("/{login}")
    public Response getPlayersGames(@PathParam("login") String login) {
        return Response.ok().entity(statsRepository.getPlayersStats(login)).build();
    }

    @GET
    @Path("/game/{gameId}")
    public Response getGame(@PathParam("gameId") Long gameId) {
        return Response.ok().entity(statsRepository.getGame(gameId)).build();
    }

    @GET
    @Path("/latestid/{login}")
    public Response getLatestGame(@PathParam("login") String login) {
        return Response.ok().entity(statsRepository.getLatesGameId(login)).build();
    }
}
