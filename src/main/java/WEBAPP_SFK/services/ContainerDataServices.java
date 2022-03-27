package WEBAPP_SFK.services;

import WEBAPP_SFK.models.BranchOffice;
import WEBAPP_SFK.models.ContainerData;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class ContainerDataServices extends DataBaseRepository<ContainerData> {
    private static ContainerDataServices instance;

    public ContainerDataServices() {
        super(ContainerData.class);
    }

    public static ContainerDataServices getInstance() {
        if(instance==null){
            instance = new ContainerDataServices();
        }
        return instance;
    }
    public Float getLastData() {
        EntityManager entityManager = getEntityManager();
        String countQ = "SELECT C.weight from ContainerData C where C.measureId=(select max(measureId)-1 from ContainerData)";
        Query countQuery = entityManager.createQuery(countQ,Float.class);
        try{
            if(countQuery != null){
                return ((Number) countQuery.getSingleResult()).floatValue();
            }
        }catch(NoResultException e){
            System.out.println(e);
        }
        return null;
    }
    public float getSecondLastValueWeight(){
        EntityManager entityManager = getEntityManager();
        Query lastSecondValue = entityManager.createQuery("SELECT WEIGHT FROM Container WHERE MEASUREID=(SELECT max(MEASUREID - 1)  FROM CONTAINERDATA",ContainerData.class);
        return (float) lastSecondValue.getSingleResult();
    }
}
