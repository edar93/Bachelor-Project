package vsb.cec0094.bachelorProject.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vsb.cec0094.bachelorProject.models.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by User on 13. 11. 2016.
 */
@Repository
@Transactional
public class TestDao {

    @PersistenceContext
    private EntityManager em;

    public List<Test> getAllTest(){
        return em.createQuery("SELECT t from Test t", Test.class).getResultList();
    }

}
