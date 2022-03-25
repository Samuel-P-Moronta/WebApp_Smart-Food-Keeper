package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Notification;
import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.services.connect.DataBaseRepository;
import WEBAPP_SFK.utilities.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class ShelfServices extends DataBaseRepository<Shelf> {
    private static ShelfServices instance;

    public ShelfServices() {
        super(Shelf.class);
    }

    public static ShelfServices getInstance() {
        if(instance==null){
            instance = new ShelfServices();
        }
        return instance;
    }
    /*
    SELECT  DISTINCT  C.ID FROM COMPANY AS C, (SELECT COMPANY_ID FROM BRANCHOFFICE B
    INNER JOIN SHELF ON B.ID = SHELF.BRANCHOFFICE_ID WHERE DEVICEID= 1) AS U
    where c.id = u.company_id

     */
    public Shelf findAllShelf() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT S FROM Shelf S");
        List<Shelf> shelfList = query.getResultList();
        if (shelfList.size() > 0) {
            return shelfList.get(0);
        }
        return null;
    }
    public List<Shelf> findShelfByBranchOffice(long idBranchOffice) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM Shelf WHERE BRANCHOFFICE_ID = :idBranchOffice",Shelf.class);
        query.setParameter("idBranchOffice",idBranchOffice);
        return query.getResultList();
    }
    public boolean createShelf(Shelf entity) throws PersistenceException {
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
