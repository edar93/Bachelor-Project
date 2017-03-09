package vsb.cec0094.bachelorProject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Response;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class JerseyTest1 extends BaseJerseyTest{

    @Test
    public void doJerseyTest() {


        final Response response = target("/getLoggedUserLogin")
                .request()
                .get();

        System.out.println(response.getEntity());


        System.out.println("ahoj >D");
        System.out.println(response.getEntity().toString() + "<<<<<<<<<<<<<<<<>");
        System.out.println(response. + "<<<<<<<<<<<<<<<<>");
        assertTrue(true);
    };
}
