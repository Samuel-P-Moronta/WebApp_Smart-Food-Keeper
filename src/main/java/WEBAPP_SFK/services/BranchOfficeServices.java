package WEBAPP_SFK.services;

import WEBAPP_SFK.models.*;
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
    public BranchOffice findBranchOfficeByAddress(String city, String direction) {
        EntityManager em = getEntityManager();
        String sql = "";
        sql += " SELECT N from BranchOffice N WHERE N.address.city = :city and N.address.direction = :direction";

        Query query = em.createQuery(sql, BranchOffice.class);
        query.setParameter("city", city);
        query.setParameter("direction",direction);
        List<BranchOffice> branchOfficeList = query.getResultList();
        if (branchOfficeList.size() > 0) {
            return branchOfficeList.get(0);
        }
        return null;
    }
}
