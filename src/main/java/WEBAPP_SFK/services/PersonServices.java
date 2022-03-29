package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Company;
import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.models.User;
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

    public Person findPersonByEmail(String email) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("SELECT P FROM Person P WHERE  P.user.email =:email");
        query.setParameter("email",email);
        List<Person> personList = query.getResultList();
        if (personList.size() > 0) {
            return personList.get(0);
        }
        //Return none
        return null;
    }
    public List<Person> findCompanyEmployees(long idCompany) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT P FROM Person P " +
                "INNER JOIN User U on P.user.email = U.email " +
                "INNER JOIN Company C ON U.company.id = C.id " +
                "WHERE U.branchOffice.id " +
                "IS NOT NULL and C.id = :idCompany");
        query.setParameter("idCompany",idCompany);
        return query.getResultList();
    }
}
