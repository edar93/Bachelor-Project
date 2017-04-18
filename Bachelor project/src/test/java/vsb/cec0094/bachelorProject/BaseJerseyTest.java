package vsb.cec0094.bachelorProject;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import javax.ws.rs.core.Application;

@ContextConfiguration(locations = {"classpath:/jersey-context-test.xml"})
public abstract class BaseJerseyTest<T> extends JerseyTest {

    private Logger logger = LoggerFactory.getLogger(getResourceClass().getName());

    @Override
    protected Application configure() {
        return getResourceConfig();
    }

    ResourceConfig getResourceConfig(){
        ResourceConfig resourceConfig = new ResourceConfig(getResourceClass());
        resourceConfig.property("contextConfigLocation", "classpath:/jersey-context-test.xml");
        resourceConfig.register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindServices(this);
            }
        });
        return resourceConfig;
    }

    protected abstract void bindServices(AbstractBinder binder);

    protected abstract Class<T> getResourceClass();

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
