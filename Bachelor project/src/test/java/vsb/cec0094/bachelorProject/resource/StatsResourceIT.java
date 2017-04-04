package vsb.cec0094.bachelorProject.resource;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vsb.cec0094.bachelorProject.BaseJerseyTest;
import vsb.cec0094.bachelorProject.dao.AccountDao;
import vsb.cec0094.bachelorProject.models.User;
import vsb.cec0094.bachelorProject.models.statsModel.StatsRecord;
import vsb.cec0094.bachelorProject.repository.StatsRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
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

    @Inject
    private AccountDao accountDao;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);

        statsRepository.setEm(em);
        accountDao.setEm(em);

        when(query.setParameter(anyString(), anyString())).thenReturn(query);
        when(query.setFirstResult(anyInt())).thenReturn(query);
        when(query.setMaxResults(anyInt())).thenReturn(query);
        when(em.createQuery(anyString(), any(Class.class))).thenReturn(query);
    }

    @Test
    public void testGetPagesCount() {
        // prepare
        final Long expectedPagesCount = 2L;
        when(query.getSingleResult()).thenReturn(2L);
        //test
        final Response response = target("/stats/pagesCount/mockedUser")
                .request()
                .get();
        Long pagesCount = response.readEntity(Long.class);
        //validation
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedPagesCount, pagesCount);
    }

    @Test
    public void testGetPlayersGamesFail() {
        //prepare
        when(em.find(StatsRecord.class, 1L)).thenReturn(null);
        //test
        final Response response = target("/stats/mockedUser/1")
                .request()
                .get();
        //validation
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetPlayersGames() {
        //prepare
        final User mockedUser = new User();
        when(em.find(User.class, "mockedUser")).thenReturn(mockedUser);

        final List<StatsRecord> expectedStatsRecordList = new ArrayList<>();
        expectedStatsRecordList.add(new StatsRecord());
        expectedStatsRecordList.add(new StatsRecord());
        when(query.getResultList()).thenReturn(expectedStatsRecordList);
        //test
        Response response = target("/stats/mockedUser/1")
                .request()
                .get();
        final List<StatsRecord> recordList = response.readEntity(new GenericType<ArrayList<StatsRecord>>() {
        });
        //validation
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedStatsRecordList, recordList);
    }

    @Test
    public void testGetGame() {
        // prepare
        final StatsRecord expectedStatsRecord = new StatsRecord();
        expectedStatsRecord.setId(1);
        expectedStatsRecord.setCreateDate(new Date(1234L));
        when(em.find(StatsRecord.class, 1L)).thenReturn(expectedStatsRecord);
        //test
        Response response = target("/stats/game/1")
                .request()
                .get();
        final StatsRecord record = response.readEntity(StatsRecord.class);
        //validation
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        assertEquals(expectedStatsRecord, record);
    }

    @Override
    protected void bindServices(AbstractBinder binder) {
        binder.bind(statsResource).to(StatsResource.class);
    }

    @Override
    protected Class<StatsResource> getResourceClass() {
        return StatsResource.class;
    }
}
