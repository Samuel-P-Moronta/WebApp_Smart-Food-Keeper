package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.models.ShelfDataJSON;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ShelfDataServices extends DataBaseRepository<ShelfData> {
    private static ShelfServices instance;

    public ShelfDataServices() {
        super(ShelfData.class);
    }
    public static ShelfServices getInstance() {
        if(instance==null){
            instance = new ShelfServices();
        }
        return instance;
    }
    public List<ShelfData> getAllShelfData(String deviceName){
        EntityManager entityManager = getEntityManager();
        String sql = "";
        sql+=" SELECT * FROM SHELF S INNER JOIN SHELF_DATA SD on DEVICE_NAME =:deviceName";

        Query query = entityManager.createNativeQuery(sql,ShelfData.class);
        query.setParameter("deviceName",deviceName);
        return query.getResultList();
    }


}
