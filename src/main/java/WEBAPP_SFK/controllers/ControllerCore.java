package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.*;
import WEBAPP_SFK.services.*;

import java.util.ArrayList;
import java.util.List;

public class ControllerCore {
    public static ControllerCore controllerCore;


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
        return ShelfDataServices.getInstance().findAll();
    }
    /*
    public List<ShelfData> getShelfDataByShelf(String deviceName){
        List<ShelfData> sh1 = new ArrayList<ShelfData>();
        for(ShelfData s: listShelfData()){
            if(s.getDeviceId().equals(deviceName)){
                sh1.add(s);
            }
        }
        return sh1;
    }

     */
    //----------------------------------SHELF-------------------------------------------------------//
    public boolean addShelf(Shelf sh){return ShelfServices.getInstance().createShelf(sh);}
    public boolean updateShelf(Shelf shelf){return ShelfServices.getInstance().update(shelf);}
    public boolean deleteShelf(Shelf shelf){return ShelfServices.getInstance().delete(shelf);}
    public boolean addShelfData(ShelfData sh){return ShelfDataServices.getInstance().create(sh);}
    public Shelf findShelfByDeviceId(String shelf){return ShelfServices.getInstance().find(shelf);}

    //----------------------------------CONTAINER--------------------------------------------------//
    public boolean addContainer(Container container){return ContainerServices.getInstance().createContainer(container);}
    public boolean updateContainer(Container container){return ContainerServices.getInstance().update(container);}
    public boolean deleteContainer(Container container){return ContainerServices.getInstance().delete(container);}
    public boolean addContainerData(ContainerData containerData){return ContainerDataServices.getInstance().create(containerData);}
    public Container findContainerById(String id){return ContainerServices.getInstance().find(id);}

    //----------------------------------ORGANIZATION-----------------------------------------------//
    public boolean createCompany(Company company){
        return CompanyServices.getInstance().update(company);
    }
    public Company findCompanyById(long id){return CompanyServices.getInstance().find(id);}
    public Company findCompanyByName(String name){return CompanyServices.getInstance().findOrganizationByName(name);}
    public Company findCompanyByBranchOffice(Long idBranchOffice){return CompanyServices.getInstance().findOrganizationByBranchOffice(idBranchOffice);}

    //--------------------------BRANCH OFFICE------------------------------------------------------------//
    public boolean createBranchOffice(BranchOffice branchOffice){
        return BranchOfficeServices.getInstance().create(branchOffice);
    }
    public boolean updateBranchOffice(BranchOffice branchOffice){return BranchOfficeServices.getInstance().update(branchOffice);}
    public boolean deleteBranchOffice(long idbranchOffice){return BranchOfficeServices.getInstance().delete(idbranchOffice);}
    public BranchOffice findBranchOfficeById(long id){return BranchOfficeServices.getInstance().find(id);}

    //----------------------------------PERSON-----------------------------------------------------//
    public boolean createPerson(Person person){return PersonServices.getInstance().create(person);}
    public boolean updatePerson(Person person){return PersonServices.getInstance().update(person);}
    public boolean deletePerson(Person person){return PersonServices.getInstance().delete(person);}
    public Person findPersonById(long idPerson){return PersonServices.getInstance().find(idPerson);}

    //----------------------------------USER-------------------------------------------------------//
    public boolean createUser(User user){return UserServices.getInstance().create(user);}
    public boolean updateUser(User user){
        return UserServices.getInstance().getInstance().update(user);
    }
    public boolean deleteUser(String email){return UserServices.getInstance().delete(email);}
    public User findUserByEmail(String email){return UserServices.getInstance().find(email);}

    //---------------------------------NOTIFICATIONS---------------------------------------------//
    public boolean createNotification(Notification notification){return NotificationServices.getInstance().create(notification);}
    public boolean deleteNotification(Notification notification){return NotificationServices.getInstance().delete(notification);}

    public boolean findNotificationByTypeAndUser(int type, User user){
        if (NotificationServices.getInstance().findNotificationByType(type,user) != null){
            return true;
        }else{
            return false;
        }
    }

    public BranchOffice findBranchOfficeAddress(Address address) {
        return BranchOfficeServices.getInstance().findBranchOfficeAddress(address);
    }

    public Person findPersonByIdentificationCard(String identificationCard) {
        return PersonServices.getInstance().findPersonByIdentificationCard(identificationCard);
    }
    public Person findPersonByEmail(String email) {
        return PersonServices.getInstance().findPersonByEmail(email);
    }
}
