package vsb.cec0094.bachelorProject;

import org.eclipse.core.runtime.Status;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vsb.cec0094.bachelorProject.repository.StatsRepository;
import vsb.cec0094.bachelorProject.resource.StatsResource;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class StatsResourceIT extends BaseJerseyTest<StatsResource> {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery query;

    @Inject
    private StatsResource statsResource;

    @Inject
    private StatsRepository statsRepository;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        statsRepository.setEm(em);

        when(query.getSingleResult()).thenReturn(2L);
        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(em.createQuery(anyString(), any(Class.class))).thenReturn(query);
    }

    @Test
    public void testGetPagesCount() {
        final Long expectedPagesCount = 2L;

        Response response = target("/stats/pagesCount/adam")
                .request()
                .get();
        Long pagesCount = response.readEntity(Long.class);

        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(pagesCount, expectedPagesCount);
    }

    @Override
    void bindServices(AbstractBinder binder) {
        binder.bind(statsResource).to(StatsResource.class);
    }

    @Override
    Class<StatsResource> getResourceClass() {
        return StatsResource.class;
    }
}
