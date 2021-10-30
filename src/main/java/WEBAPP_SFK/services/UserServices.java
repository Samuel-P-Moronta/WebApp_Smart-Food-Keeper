package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.models.User;
import WEBAPP_SFK.services.connect.DataBaseRepository;

public class UserServices extends DataBaseRepository<User> {
    public UserServices() {
        super(User.class);
    }

}
