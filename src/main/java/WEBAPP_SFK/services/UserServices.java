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


}
