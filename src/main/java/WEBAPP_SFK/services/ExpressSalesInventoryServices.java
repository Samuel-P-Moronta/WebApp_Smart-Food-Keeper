package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Client;
import WEBAPP_SFK.models.ExpressSalesInventory;
import WEBAPP_SFK.models.User;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressSalesInventoryServices extends DataBaseRepository<ExpressSalesInventory> {
    private static ExpressSalesInventoryServices instance;

    public ExpressSalesInventoryServices() {
        super(ExpressSalesInventory.class);
    }

    public static ExpressSalesInventoryServices getInstance() {
        if(instance==null){
            instance = new ExpressSalesInventoryServices();
        }
        return instance;
    }
    public List<ExpressSalesInventory> findInspectionsByUser(String email){
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT ES FROM ExpressSalesInventory ES INNER JOIN User U on ES.user.email = U.email WHERE ES.user.email =: email");
        query.setParameter("email",email);
        List<ExpressSalesInventory> esI = query.getResultList();
        return esI;
    }
}
