package vsb.cec0094.bachelorProject.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import vsb.cec0094.bachelorProject.dao.GamesHolder;
import vsb.cec0094.bachelorProject.exceptions.GameDoesNotExist;
import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.exceptions.NotPlayersTurnException;
import vsb.cec0094.bachelorProject.exceptions.TooExpensiveExpeditionException;
import vsb.cec0094.bachelorProject.gameLogic.GameManipulator;
import vsb.cec0094.bachelorProject.service.UsersProvider;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/play")
@EnableAspectJAutoProxy
@Scope("request")
@CrossOrigin
public class PlayGameResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayGameResource.class);
    @Inject
    private GamesHolder gamesHolder;
    @Inject
    private UsersProvider usersProvider;

    @POST
    @Path("/startGame")
    public Response startGame() throws CloneNotSupportedException {
        if (usersProvider.getLogin().equals(usersProvider.getGameInQueue().getOwner())) {
            gamesHolder.addGame(new GameManipulator(usersProvider.getGameInQueue()));
        }
        return Response.ok().build();
    }

    @GET
    @Path("/getMyGame")
    public Response getMyGame() {
        GameManipulator gameManipulator = usersProvider.getGameManipulator();
        if (gameManipulator != null) {
            return Response.ok().entity(usersProvider.getGameManipulator()).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @POST
    @Path("/facecard")
    public Response faceCard() throws CloneNotSupportedException, GameDoesNotExist, NotPlayersTurnException, InvalidActionException {
        try {
            usersProvider.getGameManipulatorWhenIsPlayerOnTurn().faceCard();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/pickcard")
    public Response pickCard(@RequestBody Integer id) throws CloneNotSupportedException, GameDoesNotExist, NotPlayersTurnException {
        try {
            usersProvider.getGameManipulatorWhenIsPlayerOnTurn().playerGetCardFromTable(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/pickexpedition")
    public Response pickExpedition(@RequestBody Integer id) throws CloneNotSupportedException, TooExpensiveExpeditionException, GameDoesNotExist, NotPlayersTurnException {
        try {
            usersProvider.getGameManipulatorWhenIsPlayerOnTurn().playerPickExpedition(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity(e).build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/skipaction")
    public Response skipAction() throws NotPlayersTurnException {
        usersProvider.getGameManipulatorWhenIsPlayerOnTurn().skipAction();
        return Response.ok().build();
    }

    @POST
    @Path("/applyadmiral")
    public Response applyAdmiral() throws NotPlayersTurnException {
        usersProvider.getGameManipulatorWhenIsPlayerOnTurn().applyAdmiral();
        return Response.ok().build();
    }

}
