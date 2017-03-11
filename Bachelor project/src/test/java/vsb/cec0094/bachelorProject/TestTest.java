package vsb.cec0094.bachelorProject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vsb.cec0094.bachelorProject.resource.AccountResource;

import javax.ws.rs.core.Response;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public  class TestTest extends BaseJerseyTest<AccountResource>{

    @Test
    public void doTest() {
        assertTrue(true);
    };

    @Test
    public void doJerseyTest2() {

        Response response = target("/port-royal/accounts/getLoggedUserLogin")
                .request()
                .get();
        getLogger().debug(response.getStatus() + "<<<<<<<<<<<<<<<<>");

        response = target("/accounts/getLoggedUserLogin")
                .request()
                .get();
        getLogger().debug(response.getStatus() + "<<<<<<<<<<<<<<<<>");

        assertTrue(true);
    };

    @Override
    Class getResourceClass() {
        return AccountResource.class;
    }
}