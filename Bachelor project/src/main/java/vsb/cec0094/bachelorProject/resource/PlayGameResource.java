package vsb.cec0094.bachelorProject.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import vsb.cec0094.bachelorProject.dao.GamesHolder;
import vsb.cec0094.bachelorProject.exceptions.GameDoesNotExist;
import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.exceptions.NotPlayersTurnException;
import vsb.cec0094.bachelorProject.exceptions.TooExpensiveExpeditionException;
import vsb.cec0094.bachelorProject.gameLogic.GameManipulator;
import vsb.cec0094.bachelorProject.models.LocationOnPage;
import vsb.cec0094.bachelorProject.service.UsersProvider;

import javax.ws.rs.core.Response;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Path("/play")
@EnableAspectJAutoProxy
@Scope("request")
public class PlayGameResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayGameResource.class);
    @Inject
    private GamesHolder gamesHolder;
    @Inject
    private UsersProvider usersProvider;

    @POST
    @Path("/startGame")
    public Response startGame() throws CloneNotSupportedException {
        LOGGER.debug("startGame was called");
        if (usersProvider.getLogin().equals(usersProvider.getGameInQueue().getOwner())) {
            gamesHolder.addGame(new GameManipulator(usersProvider.getGameInQueue()));
        }
        return Response.ok().build();
    }

    @GET
    @Path("/getMyGame")
    public Response getMyGame() {
        LOGGER.debug("getMyGame was called");
        return Response.ok().entity(usersProvider.getGameManipulator()).build();
    }

    @POST
    @Path("/facecard")
    public Response faceCard() throws CloneNotSupportedException, GameDoesNotExist, NotPlayersTurnException, InvalidActionException {
        LOGGER.debug("faceCard was called");
        usersProvider.getGameManipulatorWhenIsPlayerOnTurn().faceCard();
        return Response.ok().build();
    }

    @POST
    @Path("/pickcard")
    public Response pickCard(@RequestBody Integer id) throws CloneNotSupportedException, GameDoesNotExist, NotPlayersTurnException {
        LOGGER.debug("pickCard was called");
        usersProvider.getGameManipulatorWhenIsPlayerOnTurn().playerGetCardFromTable(id);
        return Response.ok().build();
    }

    @POST
    @Path("/pickexpedition")
    public Response pickExpedition(@RequestBody Integer id) throws CloneNotSupportedException, TooExpensiveExpeditionException, GameDoesNotExist, NotPlayersTurnException {
        LOGGER.debug("pickExpedition was called");
        usersProvider.getGameManipulatorWhenIsPlayerOnTurn().playerPickExpedition(id);
        return Response.ok().build();
    }

    @POST
    @Path("/skipaction")
    public Response skipAction() throws NotPlayersTurnException {
        LOGGER.debug("skipAction was called");
        usersProvider.getGameManipulatorWhenIsPlayerOnTurn().skipAction();
        return Response.ok().build();
    }

    @POST
    @Path("/applyadmiral")
    public Response applyAdmiral() throws NotPlayersTurnException {
        LOGGER.debug("evaluateAdmirals was called");
        usersProvider.getGameManipulatorWhenIsPlayerOnTurn().applyAdmiral();
        return Response.ok().build();
    }

    @GET
    @Path("/getmylocation")
    public Response getMyLocation() throws NotPlayersTurnException {
        //LOGGER.debug("getMyLocation was called");
        if (usersProvider.getGameManipulator() != null) {
            return Response.ok().entity(LocationOnPage.GAME).build();
        } else if (usersProvider.getGameInQueue() != null) {
            return Response.ok().entity(LocationOnPage.GAME_CREATION).build();
        } else {
            return Response.ok().entity(LocationOnPage.FREE).build();
        }
    }

}
