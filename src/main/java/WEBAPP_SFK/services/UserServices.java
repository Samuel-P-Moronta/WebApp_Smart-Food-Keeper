package WEBAPP_SFK.services;

import WEBAPP_SFK.controllers.ControllerCore;
import WEBAPP_SFK.models.Company;
import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.models.User;
import WEBAPP_SFK.models.enums.RoleApp;
import WEBAPP_SFK.services.connect.DataBaseRepository;
import io.javalin.core.security.Role;
import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.management.relation.RoleList;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserServices extends DataBaseRepository<User> {
    private static UserServices userServices;
    StrongPasswordEncryptor spe = new StrongPasswordEncryptor();
    public UserServices() {
        super(User.class);
    }


    public static UserServices getInstance() {
        if(userServices==null){
            userServices = new UserServices();
        }
        return userServices;
    }
    public User loginRequest(String email,String password){
        User user = find(email);
        if (user != null) {
            if(user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    public List<User> findUserByShelf(String id) {

        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT U FROM User U " +
                "INNER JOIN BranchOffice B ON U.branchOffice.id = B.id " +
                "INNER JOIN Shelf SH ON SH.branchOffice.id = B.id " +
                "INNER JOIN ShelfData SD ON SH.deviceId = SD.shelf.deviceId " +
                "WHERE SD.shelf.deviceId = :id");
        query.setParameter("id",id);
        return query.getResultList();
        //SELECT * FROM USER INNER JOIN SHELF ON USER.BRANCHOFFICE_ID WHERE SHELF.DEVICEID = 4
        //SELECT * FROM USER U INNER JOIN BRANCHOFFICE B ON U.BRANCHOFFICE_ID = B.ID INNER JOIN SHELF SH ON SH.DEVICEID = 5
        //SELECT  * FROM USER INNER JOIN BRANCHOFFICE ON USER.BRANCHOFFICE_ID = BRANCHOFFICE.ID INNER JOIN SHELF ON SHELF.DEVICEID INNER JOIN SHELF_DATA ON SHELF_DATA.DEVICEID
    }

}
