package vsb.cec0094.bachelorProject;

import org.junit.Test;
import vsb.cec0094.bachelorProject.resource.StatsResource;

import javax.ws.rs.core.Response;

import static junit.framework.TestCase.assertTrue;

public class StatsResourceIT extends BaseJerseyTest<StatsResource> {

    @Test
    public void testGetPagesCount() {
        Response response = target("/rest/stats/pagesCount/adam")
                .request()
                .get();
        getLogger().debug(response.getStatus() + "<<<<<<<<<<<<<<<<>");

        assertTrue(true);
    }

    @Override
    Class<StatsResource> getResourceClass() {
        return StatsResource.class;
    }
}
