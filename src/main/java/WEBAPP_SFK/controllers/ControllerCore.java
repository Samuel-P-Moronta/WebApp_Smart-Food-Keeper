package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.*;
import WEBAPP_SFK.services.*;
import WEBAPP_SFK.utilities.Logger;

import java.util.ArrayList;
import java.util.List;

public class ControllerCore {
    public static ControllerCore controllerCore;
    public static final PersonServices PERSON_SERVICES = new PersonServices();
    public static final UserServices USER_CREDENTIAL_SERVICES = new UserServices();
    public static final ShelfServices SHELF_SERVICES = new ShelfServices();
    public static final ShelfDataServices SHELF_DATA_SERVICES = new ShelfDataServices();
    public static final ContainerServices CONTAINER_SERVICES = new ContainerServices();
    public static final ContainerDataServices CONTAINER_DATA_SERVICES = new ContainerDataServices();



    public ControllerCore() {
    }
    /* Singleton pattern */
    public static ControllerCore getInstance() {
        if (controllerCore == null) {
            controllerCore = new ControllerCore();
        }
        return controllerCore;
    }
    //----------------------------------SHELF-------------------------------------------------------//
    public boolean addShelf(Shelf sh){
        Logger.getInstance().getLog(this.getClass()).info("Creating default shelf [...]");
        return SHELF_SERVICES.create(sh);
    }
    public boolean addShelfData(ShelfData sh){
        Logger.getInstance().getLog(this.getClass()).info("Adding shelfData to DB[...]");
        return SHELF_DATA_SERVICES.create(sh);
    }
    public Shelf getShelfByDeviceName(String shelf){
        Logger.getInstance().getLog(this.getClass()).info("Searching shelf by device name [...]");
        return SHELF_SERVICES.find(shelf);
    }

    public List<ShelfData> listShelfData(){
        return SHELF_DATA_SERVICES.findAll();
    }
    public List<ShelfData> getShelfDataByShelf(String deviceName){
        List<ShelfData> sh1 = new ArrayList<ShelfData>();
        for(ShelfData s: listShelfData()){
            if(s.getShelf().getDevice_name().equals(deviceName)){
                sh1.add(s);
            }
        }
        return sh1;
    }
    //----------------------------------CONTAINER--------------------------------------------------//
    public boolean createContainer(Container container){
        Logger.getInstance().getLog(this.getClass()).info("Creating default container [...]");
        return CONTAINER_SERVICES.create(container);
    }
    public boolean createContainerData(ContainerData containerData){
        Logger.getInstance().getLog(this.getClass()).info("Creating default container data [...]");
        return CONTAINER_DATA_SERVICES.create(containerData);
    }
    public Container findContainerById(int id){
        Logger.getInstance().getLog(this.getClass()).info("Searching container by id [...]");
        return CONTAINER_SERVICES.find(id);
    }
    //----------------------------------ORGANIZATION-----------------------------------------------//
    public Organization findOrganizationById(int id){
        return OrganizationServices.getInstance().find(id);
    }
    public boolean createOrganization(Organization organization){
        Logger.getInstance().getLog(this.getClass()).info("Creating default organizations [...]");
        return OrganizationServices.getInstance().update(organization);
    }
    public boolean createBranchOffice(BranchOffice branchOffice){
        Logger.getInstance().getLog(this.getClass()).info("Creating default Branch office [...]");
        return BranchOfficeServices.getInstance().create(branchOffice);
    }
    //----------------------------------PERSON-----------------------------------------------------//
    public boolean createPerson(Person person){
        Logger.getInstance().getLog(this.getClass()).info("Creating default person [...]");
        return PersonServices.getInstance().create(person);
    }
    //----------------------------------USER-------------------------------------------------------//
    public boolean createUser(User user){
        Logger.getInstance().getLog(this.getClass()).info("Creating default person [...]");
        return UserServices.getInstance().create(user);
    }
    public User findUserByEmail(String email){
        Logger.getInstance().getLog(this.getClass()).info("Searching user by email [...]");
        return UserServices.getInstance().find(email);
    }

}
