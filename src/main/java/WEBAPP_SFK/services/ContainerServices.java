package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Container;
import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.services.connect.DataBaseRepository;
import WEBAPP_SFK.utilities.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class ContainerServices  extends DataBaseRepository<Container> {
    private static ContainerServices instance;

    public ContainerServices() {
        super(Container.class);
    }

    public static ContainerServices getInstance() {
        if(instance==null){
            instance = new ContainerServices();
        }
        return instance;
    }
    public List<Container> findContainerfByBranchOffice(long idBranchOffice) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT C FROM Container C WHERE C.branchOffice.id = :idBranchOffice");
        query.setParameter("idBranchOffice",idBranchOffice);
        return query.getResultList();
    }
    public boolean createContainer(Container entity) throws PersistenceException {
        boolean state = false;
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
            state = true;
        } catch (Exception e){
            Logger.getInstance().getLog(getClass()).error(String.format("Error creating entity - Exception message: %s", e.getMessage()));
            e.printStackTrace();
        } finally {
            em.close();
        }
        return state;
    }

}
