package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.models.enums.RoleApp;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.management.relation.Role;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static WEBAPP_SFK.models.enums.RoleApp.ROLE_EMPLOYEE;

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
    public List<Person> findPersonByRole() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT P FROM Person P JOIN P.user.rolesList u WHERE u = '1'");
        return query.getResultList();
    }

}
