package WEBAPP_SFK.services;

import WEBAPP_SFK.models.BranchOffice;
import WEBAPP_SFK.models.Notification;
import WEBAPP_SFK.models.User;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class BranchOfficeServices extends DataBaseRepository<BranchOffice> {
    private static BranchOfficeServices instance;

    public BranchOfficeServices() {

        super(BranchOffice.class);
    }

    public static BranchOfficeServices getInstance() {
        if(instance==null){
            instance = new BranchOfficeServices();
        }
        return instance;
    }
    public BranchOffice findBranchOfficeDirection(String name) {
        EntityManager em = getEntityManager();
        String sql = "";
        sql += " SELECT N from BranchOffice N WHERE N.address.direction = :name";

        Query query = em.createQuery(sql, BranchOffice.class);
        query.setParameter("name", name);
        List<BranchOffice> branchOfficeList = query.getResultList();
        if (branchOfficeList.size() > 0) {
            return branchOfficeList.get(0);
        }
        return null;
    }
    public BranchOffice findBranchOfficeByCompany(long idCompany) {
        EntityManager em = getEntityManager();
        String sql = "";
        sql+= "SELECT * FROM BRANCHOFFICE WHERE BRANCHOFFICE.COMPANY_ID = :idCompany";
        Query query = em.createQuery(sql, BranchOffice.class);
        query.setParameter("idCompany", idCompany);
        List<BranchOffice> branchOfficeList = query.getResultList();
        if (branchOfficeList.size() > 0) {
            return branchOfficeList.get(0);
        }
        return null;
    }

}
