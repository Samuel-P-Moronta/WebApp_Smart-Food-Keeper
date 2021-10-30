package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.models.User;
import WEBAPP_SFK.services.PersonServices;
import WEBAPP_SFK.services.ShelfDataServices;
import WEBAPP_SFK.services.ShelfServices;
import WEBAPP_SFK.services.UserServices;

public class ControllerCore {
    public static ControllerCore controllerCore;
    public static final PersonServices personServices = new PersonServices();
    public static final UserServices userServices = new UserServices();
    public static final ShelfServices shelfServices = new ShelfServices();
    public static final ShelfDataServices shelfDataServices = new ShelfDataServices();





    public ControllerCore() {
    }
    /* Singleton pattern */
    public static ControllerCore getInstance() {
        if (controllerCore == null) {
            controllerCore = new ControllerCore();
        }
        return controllerCore;
    }
    public boolean addPerson(Person p){
        return personServices.create(p);
    }
    public boolean addUser(User u){
        return userServices.create(u);
    }
    public boolean addShelf(Shelf sh){
        return shelfServices.create(sh);
    }
    public boolean addShelfData(ShelfData sh){
        return shelfDataServices.create(sh);
    }

}
