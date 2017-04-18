package vsb.cec0094.bachelorProject.resource;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vsb.cec0094.bachelorProject.BaseJerseyTest;
import vsb.cec0094.bachelorProject.gameLogic.GameManipulator;
import vsb.cec0094.bachelorProject.service.UsersProvider;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class PlayGameResourceTest extends BaseJerseyTest<PlayGameResource> {

    @Inject
    private PlayGameResource playGameResource;

    @Inject
    private UsersProvider usersProvider;

    private GameManipulator expectedGame;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        expectedGame = new GameManipulator();
        expectedGame.setId(666);

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

    @Override
    protected void bindServices(AbstractBinder binder) {
        binder.bind(playGameResource).to(PlayGameResource.class);
    }

    @Override
    protected Class<PlayGameResource> getResourceClass() {
        return PlayGameResource.class;
    }
}
