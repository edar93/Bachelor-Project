package vsb.cec0094.bachelorProject;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;

import javax.ws.rs.core.Application;

@ContextConfiguration(locations = {"classpath:jersey-context-test.xml"})
public class BaseJerseyTest<T> extends JerseyTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected Application configure() {
        return new ResourceConfig();
    }

}
