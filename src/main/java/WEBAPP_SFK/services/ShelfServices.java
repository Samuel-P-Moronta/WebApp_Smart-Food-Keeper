package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.models.ShelfJSON;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
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
    public ShelfJSON findAllShelf() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT S FROM Shelf S");
        List<ShelfJSON> shelfList = query.getResultList();
        if (shelfList.size() > 0) {
            return shelfList.get(0);
        }
        return null;
    }

}
