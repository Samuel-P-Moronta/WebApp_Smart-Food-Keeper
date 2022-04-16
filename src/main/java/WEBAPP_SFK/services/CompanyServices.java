package WEBAPP_SFK.services;

import WEBAPP_SFK.controllers.ControllerCore;
import WEBAPP_SFK.models.*;
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
        sql += " SELECT DISTINCT * FROM COMPANY INNER JOIN BRANCHOFFICE ON COMPANY.ID = BRANCHOFFICE.COMPANY_ID WHERE BRANCHOFFICE.ID = :idBranchOffice";
        Query query = em.createNativeQuery(sql, Company.class);
        query.setParameter("idBranchOffice", idBranchOffice);
        List<Company> companyList = query.getResultList();
        if (companyList.size() > 0) {
            return companyList.get(0);
        }
        //Return none
        return null;
    }

    public Company findOrganizationByNameRnc(String rnc) {
        EntityManager em = getEntityManager();
        String sql = "";
        sql += "SELECT O from Company O WHERE O.rnc = :rnc";
        Query query = em.createQuery(sql, Company.class);
        query.setParameter("rnc", rnc);

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
