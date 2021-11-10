package WEBAPP_SFK.services;

import WEBAPP_SFK.models.User;
import WEBAPP_SFK.services.connect.DataBaseRepository;

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
