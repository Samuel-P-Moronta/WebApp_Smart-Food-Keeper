package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Notification;
import WEBAPP_SFK.models.Organization;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class OrganizationServices extends DataBaseRepository<Organization> {
    private static OrganizationServices instance;

    public OrganizationServices() {
        super(Organization.class);
    }
    public static OrganizationServices getInstance() {
        if(instance==null){
            instance = new OrganizationServices();
        }
        return instance;
    }

    public Organization findOrganizationByBranchOffice(long idBranchOffice) {
        EntityManager em = getEntityManager();
        String sql = "";
        sql += " SELECT E from Organization E INNER JOIN BranchOffice S on E.id = S.id";
        sql += " WHERE S.id=:branchOffice";
        Query query = em.createQuery(sql, Organization.class);
        query.setParameter("branchOffice", idBranchOffice);
        List<Organization> organizationList = query.getResultList();
        if (organizationList.size() > 0) {
            return organizationList.get(0);
        }
        //Return none
        return null;
    }

    public Organization findOrganizationByName(String name) {
        EntityManager em = getEntityManager();
        String sql = "";
        sql += "SELECT O from Organization O WHERE O.name = :name";
        Query query = em.createQuery(sql, Organization.class);
        query.setParameter("name", name);
        List<Organization> organizationList = query.getResultList();
        if (organizationList.size() > 0) {
            return organizationList.get(0);
        }
        return null;
    }
}
