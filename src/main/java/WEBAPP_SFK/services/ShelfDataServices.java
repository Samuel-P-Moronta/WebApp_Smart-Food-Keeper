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
        String consulta = "";
       // consulta+='SELECT * from'

        Query query = entityManager.createQuery("SELECT SD FROM ShelfData SD WHERE SD.shelf.id = :id");
        query.setParameter("id",deviceName);
        return query.getResultList();
    }


}
