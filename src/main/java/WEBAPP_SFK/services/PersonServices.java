package WEBAPP_SFK.services;

import WEBAPP_SFK.models.BranchOffice;
import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.models.User;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class PersonServices extends DataBaseRepository<Person> {

    private static PersonServices personServices;

    public PersonServices() {
        super(Person.class);
    }


    public static PersonServices getInstance() {
        if(personServices==null){
            personServices = new PersonServices();
        }
        return personServices;
    }
    public Person findPersonByIdentificationCard(String identificationCard) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT P FROM Person P WHERE P.identificationCard = :identificationCard");
        query.setParameter("identificationCard",identificationCard);
        List<Person> personList = query.getResultList();
        if (personList.size() > 0) {
            return personList.get(0);
        }
        return null;
    }


}
