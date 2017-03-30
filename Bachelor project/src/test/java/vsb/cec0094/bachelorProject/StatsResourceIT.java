package vsb.cec0094.bachelorProject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vsb.cec0094.bachelorProject.resource.StatsResource;

import javax.ws.rs.core.Response;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class StatsResourceIT extends BaseJerseyTest<StatsResource> {

    @Test
    public void testGetPagesCount() {
        Response response = target("/stats/pagesCount/adam")
                .request()
                .get();
        getLogger().debug(response.getStatus() + "<<<<<<<<<<<<<<<<>");

        Response expectedResponse = Response.ok().entity(4L).build();
        assertThat(response, is(expectedResponse));
        assertTrue(true);
    }

    @Override
    Class<StatsResource> getResourceClass() {
        return StatsResource.class;
    }
}
