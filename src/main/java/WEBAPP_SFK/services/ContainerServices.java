package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Container;
import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
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

}
