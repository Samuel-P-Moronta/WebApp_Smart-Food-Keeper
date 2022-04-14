package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class ShelfDataServices extends DataBaseRepository<ShelfData> {
    private static ShelfDataServices instance;

    public ShelfDataServices() {
        super(ShelfData.class);
    }
    public static ShelfDataServices getInstance() {
        if(instance==null){
            instance = new ShelfDataServices();
        }
        return instance;
    }
    public Float getLastEnvironmentalData(float x){
        if(x==0){
            EntityManager entityManager = getEntityManager();
            String countQ = "SELECT sd.temperature FROM ShelfData sd WHERE measureId=(SELECT max(measureId) FROM ShelfData )";
            Query countQuery = entityManager.createQuery(countQ);
            return ((Number) countQuery.getSingleResult()).floatValue();
        }
        if(x==1){
            EntityManager entityManager = getEntityManager();
            String countQ = "SELECT sd.humidity FROM ShelfData sd WHERE measureId=(SELECT max(measureId) FROM ShelfData )";
            Query countQuery = entityManager.createQuery(countQ);
            return ((Number) countQuery.getSingleResult()).floatValue();
        }
        return x;
    }
    public String getLastFruitType(){
        EntityManager entityManager = getEntityManager();
        String countQ = "SELECT sd.fruitType FROM ShelfData sd WHERE measureId=(SELECT max(measureId) FROM ShelfData )";
        Query countQuery = entityManager.createQuery(countQ);
        return (String) countQuery.getSingleResult();
    }
    public Date getLastDateMeasure(){
        EntityManager entityManager = getEntityManager();
        String countQ = "SELECT sd.currentSampleDate FROM ShelfData sd WHERE measureId=(SELECT max(measureId) FROM ShelfData )";
        Query countQuery = entityManager.createQuery(countQ);
        return (Date) countQuery.getSingleResult();

    }

    public int getLastRecognitionData(int x){
        EntityManager entityManager = getEntityManager();
        String countQ = "";
        switch (x) {
            case 0:
                countQ = "SELECT sd.fruitCant FROM ShelfData sd WHERE measureId=(SELECT max(measureId) FROM ShelfData )";
                Query countQuery = entityManager.createQuery(countQ);
                x = ((Number) countQuery.getSingleResult()).intValue();
                break;
            case 1:
                countQ = "SELECT sd.cantOverripe FROM ShelfData sd WHERE measureId=(SELECT max(measureId) FROM ShelfData )";
                Query countQuery1 = entityManager.createQuery(countQ);
                x =  ((Number) countQuery1.getSingleResult()).intValue();
                break;
            case 2:
                countQ = "SELECT sd.cantRipe FROM ShelfData sd WHERE measureId=(SELECT max(measureId) FROM ShelfData )";
                Query countQuery2 = entityManager.createQuery(countQ);
                x =  ((Number) countQuery2.getSingleResult()).intValue();
                break;
            case 3:
                countQ = "SELECT sd.cantUnripe FROM ShelfData sd WHERE measureId=(SELECT max(measureId) FROM ShelfData )";
                Query countQuery3 = entityManager.createQuery(countQ);
                x = ((Number) countQuery3.getSingleResult()).intValue();
                break;
            default:
                //code something


        }
        return x;
    }
    public List<ShelfData> findAllShelfDataByShelfId(String deviceId) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT S FROM ShelfData S WHERE S.shelf.deviceId = :deviceId",ShelfData.class);
        query.setParameter("deviceId",deviceId);
        return query.getResultList();
    }
}
