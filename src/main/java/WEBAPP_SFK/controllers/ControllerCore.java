package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.*;
import WEBAPP_SFK.services.*;

import java.util.ArrayList;
import java.util.List;

public class ControllerCore {
    public static ControllerCore controllerCore;
    public static final PersonServices PERSON_SERVICES = new PersonServices();
    public static final UserServices USER_SERVICES = new UserServices();
    public static final ShelfServices SHELF_SERVICES = new ShelfServices();
    public static final ShelfDataServices SHELF_DATA_SERVICES = new ShelfDataServices();
    public static final ContainerServices CONTAINER_SERVICES = new ContainerServices();
    public static final ContainerDataServices CONTAINER_DATA_SERVICES = new ContainerDataServices();
    public static final CompanyServices ORGANIZATION_SERVICES = new CompanyServices();
    private static final BranchOfficeServices BRANCH_OFFICE_SERVICES = new BranchOfficeServices();
    private static final NotificationServices NOTIFICATION_SERVICES = new NotificationServices();



    public ControllerCore() {
    }
    /* Singleton pattern */
    public static ControllerCore getInstance() {
        if (controllerCore == null) {
            controllerCore = new ControllerCore();
        }
        return controllerCore;
    }

    public List<ShelfData> listShelfData(){
        return SHELF_DATA_SERVICES.findAll();
    }
    public List<ShelfData> getShelfDataByShelf(String deviceName){
        List<ShelfData> sh1 = new ArrayList<ShelfData>();
        for(ShelfData s: listShelfData()){
            if(s.getDeviceId().equals(deviceName)){
                sh1.add(s);
            }
        }
        return sh1;
    }
    //----------------------------------SHELF-------------------------------------------------------//
    public boolean addShelf(Shelf sh){return SHELF_SERVICES.create(sh);}
    public boolean updateShelf(Shelf shelf){return SHELF_SERVICES.update(shelf);}
    public boolean deleteShelf(Shelf shelf){return SHELF_SERVICES.delete(shelf);}
    public boolean addShelfData(ShelfData sh){return SHELF_DATA_SERVICES.create(sh);}
    public Shelf findShelfByDeviceId(String shelf){return SHELF_SERVICES.find(shelf);}

    //----------------------------------CONTAINER--------------------------------------------------//
    public boolean addContainer(Container container){return CONTAINER_SERVICES.create(container);}
    public boolean updateContainer(Container container){return CONTAINER_SERVICES.update(container);}
    public boolean deleteContainer(Container container){return CONTAINER_SERVICES.delete(container);}
    public boolean createContainerData(ContainerData containerData){return CONTAINER_DATA_SERVICES.create(containerData);}
    public Container findContainerById(long id){return CONTAINER_SERVICES.find(id);}

    //----------------------------------ORGANIZATION-----------------------------------------------//
    public boolean createOrganization(Company company){
        return ORGANIZATION_SERVICES.update(company);
    }
    public Company findOrganizationById(long id){return ORGANIZATION_SERVICES.find(id);}
    public Company findOrganizationByName(String name){return ORGANIZATION_SERVICES.findOrganizationByName(name);}
    public Company findOrganizationByBranchOffice(Long idBranchOffice){return ORGANIZATION_SERVICES.findOrganizationByBranchOffice(idBranchOffice);}

    //--------------------------BRANCH OFFICE------------------------------------------------------------//
    public boolean createBranchOffice(BranchOffice branchOffice){
        return BRANCH_OFFICE_SERVICES.create(branchOffice);
    }
    public boolean updateBranchOffice(BranchOffice branchOffice){return BRANCH_OFFICE_SERVICES.update(branchOffice);}
    public boolean deleteBranchOffice(long idbranchOffice){return BRANCH_OFFICE_SERVICES.delete(idbranchOffice);}
    public BranchOffice findBranchOfficeById(long id){return BRANCH_OFFICE_SERVICES.find(id);}

    //----------------------------------PERSON-----------------------------------------------------//
    public boolean createPerson(Person person){return PERSON_SERVICES.getInstance().create(person);}
    public boolean updatePerson(Person person){return PERSON_SERVICES.update(person);}
    public boolean deletePerson(Person person){return PERSON_SERVICES.delete(person);}
    public Person findPersonById(long idPerson){return PERSON_SERVICES.find(idPerson);}

    //----------------------------------USER-------------------------------------------------------//
    public boolean createUser(User user){return USER_SERVICES.getInstance().create(user);}
    public boolean updateUser(User user){return USER_SERVICES.update(user);}
    public boolean deleteUser(User user){return USER_SERVICES.delete(user);}
    public User findUserByEmail(String email){return UserServices.getInstance().find(email);}

    //---------------------------------NOTIFICATIONS---------------------------------------------//
    public boolean createNotification(Notification notification){return NOTIFICATION_SERVICES.create(notification);}
    public boolean deleteNotification(Notification notification){return NOTIFICATION_SERVICES.delete(notification);}

    public boolean findNotificationByTypeAndUser(int type, User user){
        if (NOTIFICATION_SERVICES.findNotificationByType(type,user) != null){
            return true;
        }else{
            return false;
        }
    }

    public BranchOffice findBranchOfficeDirection(String branchOfficeDirection) {
        return BRANCH_OFFICE_SERVICES.findBranchOfficeDirection(branchOfficeDirection);
    }

    public Person findPersonByIdentificationCard(String identificationCard) {
        return PERSON_SERVICES.findPersonByIdentificationCard(identificationCard);
    }
}
