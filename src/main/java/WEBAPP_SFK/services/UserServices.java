package WEBAPP_SFK.services;

import WEBAPP_SFK.controllers.ControllerCore;
import WEBAPP_SFK.models.Company;
import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.models.User;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.management.relation.RoleList;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserServices extends DataBaseRepository<User> {
    private static UserServices userCredentialServices;

    public UserServices() {
        super(User.class);
    }


    public static UserServices getInstance() {
        if(userCredentialServices==null){
            userCredentialServices = new UserServices();
        }
        return userCredentialServices;
    }


}
