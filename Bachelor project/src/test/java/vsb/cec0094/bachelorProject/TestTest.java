package vsb.cec0094.bachelorProject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vsb.cec0094.bachelorProject.resource.AdministrationResource;

import javax.ws.rs.core.Response;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public  class TestTest extends BaseJerseyTest<AdministrationResource>{

    @Test
    public void doTest() {
        assertTrue(true);
    };

    @Test
    public void doJerseyTest2() {
        Response response = target("/rest/administration/test")
//        Response response = target("/accounts/getLoggedUserLogin")
                .request()
                .get();
        getLogger().debug(response.getStatus() + "<<<<<<<<<<<<<<<<>");

        assertTrue(true);
    };

    @Override
    Class getResourceClass() {
        return AdministrationResource.class;
    }
}