package WEBAPP_SFK.services;

import WEBAPP_SFK.models.*;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
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
    public BranchOffice findBranchOfficeAddress(Address address) {
        EntityManager em = getEntityManager();
        String sql = "";
        sql += " SELECT N from BranchOffice N WHERE N.address.direction = :city AND N.address.direction = :direction";

        Query query = em.createQuery(sql, BranchOffice.class);
        query.setParameter("city", address.getCity());
        query.setParameter("direction", address.getDirection());
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
    public BranchOffice findBranchOfficeByUserEmployee(String email) {
        EntityManager em = getEntityManager();
        String sql = "SELECT B from BranchOffice B INNER JOIN User U ON B.id = U.branchOffice.id WHERE U.email = :email";
        Query query = em.createQuery(sql, BranchOffice.class);
        query.setParameter("email", email);
        List<BranchOffice> branchOfficeList = query.getResultList();
        if (branchOfficeList.size() > 0) {
            return branchOfficeList.get(0);
        }
        return null;
    }
    public BranchOffice createBranchOffice(Address address, Company company){
        BranchOffice branchOffice1 = findBranchOfficeByAddress(address.getCity(), address.getDirection());
        Company company1 = CompanyServices.getInstance().find(company.getId());
        if(company1 !=null && !(company1.hasThisBranchOffice(branchOffice1))){
            if(branchOffice1 == null){
                branchOffice1 = new BranchOffice(address,company1);
                System.out.println("Branch office was created successfully");
                create(branchOffice1);
            }else{
                System.out.println("This branch office wasn't created successfully");
            }
        }
        return branchOffice1;
    }
}
