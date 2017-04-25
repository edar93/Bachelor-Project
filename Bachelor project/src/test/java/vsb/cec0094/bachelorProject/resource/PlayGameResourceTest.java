package vsb.cec0094.bachelorProject.resource;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vsb.cec0094.bachelorProject.BaseJerseyTest;
import vsb.cec0094.bachelorProject.dao.GamesHolder;
import vsb.cec0094.bachelorProject.exceptions.GameOverExeption;
import vsb.cec0094.bachelorProject.gameLogic.GameManipulator;
import vsb.cec0094.bachelorProject.models.GameInQueue;
import vsb.cec0094.bachelorProject.models.User;
import vsb.cec0094.bachelorProject.service.UsersProvider;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class PlayGameResourceTest extends BaseJerseyTest<PlayGameResource> {

    private final String mockedUser = "MOCKED_USER";
    private final String mockedUser2 = "MOCKED_USER2";
    @Inject
    private PlayGameResource playGameResource;
    @Inject
    private UsersProvider usersProvider;
    @Inject
    private GamesHolder gamesHolder;
    private GameManipulator expectedGame;
    private GameInQueue preparedGameInQueue;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        User user1 = new User();
        User user2 = new User();
        user1.setLogin(mockedUser);
        user2.setLogin(mockedUser2);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        preparedGameInQueue = new GameInQueue();
        preparedGameInQueue.setId(333);
        preparedGameInQueue.setMaxPlayersCount(2);
        preparedGameInQueue.setPlayersList(userList);
        preparedGameInQueue.setOwner(mockedUser);

        expectedGame = new GameManipulator();
        expectedGame.setId(222);

        when(usersProvider.getGameInQueue()).thenReturn(preparedGameInQueue);
        when(usersProvider.getLogin()).thenReturn(mockedUser);
        when(usersProvider.getGameManipulator()).thenReturn(expectedGame);
    }

    @Test
    public void testGetMyGame() {
        //test
        final Response response = target("/play/getMyGame")
                .request()
                .get();
        GameManipulator game = response.readEntity(GameManipulator.class);
        //validation
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedGame, game);
    }

    @Test
    public void testStartGameSuccessfully() {
        //prepare
        preparedGameInQueue.setOwner(mockedUser);
//        Entity<Integer> expeditionId = Entity.entity(new Integer(1), MediaType.APPLICATION_JSON);
        //test
        final Response response = target("/play/startGame")
                .request()
                .post(null);
        GameManipulator game = response.readEntity(GameManipulator.class);
        //validation
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        GameManipulator gameManipulator;
        try {
            gameManipulator = gamesHolder.getGame(333);
            assertNotNull(gameManipulator);
        } catch (GameOverExeption gameOverExeption) {
            gameOverExeption.printStackTrace();
        }
    }

    @Test
    public void testStartGameWhenNotOwner() {
        //prepare
        preparedGameInQueue.setOwner(mockedUser2);
        //test
        final Response response = target("/play/startGame")
                .request()
                .post(null);
        GameManipulator game = response.readEntity(GameManipulator.class);
        //validation
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
        GameManipulator gameManipulator;
        try {
            gameManipulator = gamesHolder.getGame(333);
            assertNull(gameManipulator);
        } catch (GameOverExeption gameOverExeption) {
            gameOverExeption.printStackTrace();
        }
    }

    @Override
    protected void bindServices(AbstractBinder binder) {
        binder.bind(playGameResource).to(PlayGameResource.class);
    }

    @Override
    protected Class<PlayGameResource> getResourceClass() {
        return PlayGameResource.class;
    }
}
