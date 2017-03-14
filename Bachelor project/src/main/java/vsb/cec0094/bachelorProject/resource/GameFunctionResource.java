package vsb.cec0094.bachelorProject.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.exceptions.NoEmptyPlaceInGame;
import vsb.cec0094.bachelorProject.models.GameInQueue;
import vsb.cec0094.bachelorProject.service.UsersProvider;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/game")
@EnableAspectJAutoProxy
@Scope("request")
public class GameFunctionResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameFunctionResource.class);

    @Inject
    private GameDao gameDao;
    @Inject
    private UsersProvider usersProvider;

    @POST
    @Path("/cratenewgame")
    public Response createGameInQueue() {
        GameInQueue gameInQueue = new GameInQueue();
        gameInQueue.setMaxPlayersCount(3);
        gameInQueue.setOwner(usersProvider.getLogin());

        gameDao.createGameInQueue(gameInQueue);
        return Response.ok().build();
    }

    @GET
    @Path("/basegamestatus")
    public Response getBaseGameStatus() {
        return Response.ok().entity(gameDao.getGameInQueue(usersProvider.getLogin())).build();
    }

    @GET
    @Path("/getallgamesinqueue")
    public Response getAllGames() {
        return Response.ok().entity(gameDao.getAllGames()).build();
    }

    @PUT
    @Path("/joingame")
    public Response joinGame(@RequestBody Integer id) {
        try {
            gameDao.joinGame(id, usersProvider.getLogin());
        } catch (NoEmptyPlaceInGame noEmptyPlaceInGame) {
            LOGGER.debug(usersProvider.getLogin() + " tried to join full game:" + id);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/getplayersgame")
    public Response getPlayersGame(@RequestBody String player) {
        return Response.ok().entity(gameDao.getPlayersGame(player)).build();
    }

    @POST
    @Path("/leftgame")
    public Response leftGame() {
        gameDao.leftGame(usersProvider.getLogin(), usersProvider.getGameInQueue());
        return Response.ok().build();
    }

    @POST
    @Path("/kick")
    public Response kickPlayer(@RequestBody String player) {
        gameDao.leftGame(player, gameDao.getPlayersGame(player));
        return Response.ok().build();
    }

    @POST
    @Path("/changeplayersmaxcount")
    public Response changeMaxPlayersCount(@RequestBody Integer newCount) {
        if (usersProvider.getGameInQueue().getPlayersList().size() <= newCount){
            gameDao.setNewMaxPlayersCount(newCount, usersProvider.getGameInQueue());
        }
        return Response.ok().build();
    }

}
