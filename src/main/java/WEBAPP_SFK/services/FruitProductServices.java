package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Container;
import WEBAPP_SFK.models.FruitProduct;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class FruitProductServices extends DataBaseRepository<FruitProduct> {
    private static FruitProductServices instance;

    public FruitProductServices() {
        super(FruitProduct.class);
    }

    public static FruitProductServices getInstance() {
        if(instance==null){
            instance = new FruitProductServices();
        }
        return instance;
    }
    public FruitProduct findProductByName(String name){
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT F FROM FruitProduct F WHERE F.fruitType = :name");
        query.setParameter("name",name);
        List<FruitProduct> fp = query.getResultList();
        if(fp.size() > 0){
            return fp.get(0);
        }
        return null;
    }
}
