package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Client;
import WEBAPP_SFK.models.Company;
import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ClientServices extends DataBaseRepository<Client> {
    private static ClientServices instance;

    public ClientServices() {
        super(Client.class);
    }

    public static ClientServices getInstance() {
        if(instance==null){
            instance = new ClientServices();
        }
        return instance;
    }
    public Client findClientByEmail(String email) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT C FROM Client C WHERE C.email = :email");
        query.setParameter("email",email);
        List<Client> clientList = query.getResultList();
        if (clientList.size() > 0) {
            return clientList.get(0);
        }
        return null;
    }


}
