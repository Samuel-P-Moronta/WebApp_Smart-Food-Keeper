package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Company;
import WEBAPP_SFK.models.User;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class CompanyServices extends DataBaseRepository<Company> {
    private static CompanyServices instance;

    public CompanyServices() {
        super(Company.class);
    }
    public static CompanyServices getInstance() {
        if(instance==null){
            instance = new CompanyServices();
        }
        return instance;
    }

    public Company findOrganizationByBranchOffice(Long idBranchOffice) {
        EntityManager em = getEntityManager();
        String sql = "";
        sql += " SELECT E from Company E INNER JOIN BranchOffice S on E.id = S.id";
        sql += " WHERE S.id=:branchOffice";
        Query query = em.createQuery(sql, Company.class);
        query.setParameter("branchOffice", idBranchOffice);
        List<Company> companyList = query.getResultList();
        if (companyList.size() > 0) {
            return companyList.get(0);
        }
        //Return none
        return null;
    }

    public Company findOrganizationByName(String name) {
        EntityManager em = getEntityManager();
        String sql = "";
        sql += "SELECT O from Company O WHERE O.name = :name";
        Query query = em.createQuery(sql, Company.class);
        query.setParameter("name", name);
        List<Company> companyList = query.getResultList();
        if (companyList.size() > 0) {
            return companyList.get(0);
        }
        return null;
    }
    public Company findCompanyByOwnerEmail(String email){

        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT C FROM Company C INNER JOIN User U on U.email = :email");
        query.setParameter("email",email);
        List<Company> companyList = query.getResultList();
        if (companyList.size() > 0) {
            return companyList.get(0);
        }
        return null;
    }
}
