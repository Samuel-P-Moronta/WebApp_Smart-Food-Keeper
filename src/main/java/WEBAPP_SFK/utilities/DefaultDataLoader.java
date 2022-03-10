package WEBAPP_SFK.utilities;


import WEBAPP_SFK.controllers.ControllerCore;
import WEBAPP_SFK.models.*;
import WEBAPP_SFK.models.enums.RoleApp;
import WEBAPP_SFK.services.*;

import java.util.*;

public class DefaultDataLoader {
    private static DefaultDataLoader instance;

    private UserServices userCredentialServices;
    private NotificationServices notificationServices;


    public DefaultDataLoader() {

    }

    public boolean addUserCredential(User user){
        return UserServices.getInstance().create(user);
    }

    public static DefaultDataLoader getInstance() {
        if(instance==null){
            instance = new DefaultDataLoader();
        }
        return instance;
    }
    public void createDefaultCompany(){
        Company company1 = new Company("El nacional");
        Company company2 = new Company("El Bravo");
        Company company3 = new Company("Caco de botella");

        ControllerCore.getInstance().createOrganization(company1);
        ControllerCore.getInstance().createOrganization(company2);
        ControllerCore.getInstance().createOrganization(company3);
    }
    public void createDefaultBranchOffice(){
        Address address = new Address("Navarrete","Calle Barrio Duarte" );
        Address address1 = new Address("Villa Gonzalez", "Calle principal");
        Address address2 = new Address("Esperanza", "Calle secundaria");
        Address address3 = new Address("Santo Domingo Oeste", "Timotes");
        Address address4 = new Address("Boca Chica", "Calle 24");


        ControllerCore controllerCore1 = new ControllerCore();

        BranchOffice branchOffice1 = new BranchOffice(address, controllerCore1.findOrganizationByName("El nacional"));
        BranchOffice branchOffice2 = new BranchOffice(address1, controllerCore1.findOrganizationByName("El Bravo"));
        BranchOffice branchOffice3 = new BranchOffice(address2, controllerCore1.findOrganizationByName("Caco de botella"));
        BranchOffice branchOffice4 = new BranchOffice(address3, controllerCore1.findOrganizationByName("El nacional"));
        BranchOffice branchOffice5 = new BranchOffice(address4,controllerCore1.findOrganizationByName("El nacional"));


        ControllerCore.getInstance().createBranchOffice(branchOffice1);
        ControllerCore.getInstance().createBranchOffice(branchOffice2);
        ControllerCore.getInstance().createBranchOffice(branchOffice3);
        ControllerCore.getInstance().createBranchOffice(branchOffice4);
        ControllerCore.getInstance().createBranchOffice(branchOffice5);
    }
    public void createDefaultSuperUser(){
        User userAux = new User("root@gmail.com","123", Set.of(RoleApp.ROLE_ROOT));
        if(ControllerCore.getInstance().findUserByEmail("root@gmail.com") == null){
            ControllerCore.getInstance().createUser(userAux);
       }
    }
    public void createDefaultData(){
        //Root user
        createDefaultSuperUser();
        //Admin
        createDefaultAdmin();
        //BranchOffice
        createDefaultBranchOffice();
        //Employee
        createDefaultEmployee();
        //Company
        createDefaultCompany();
        //Shelf
        createDefaultShelf();
        //Container
        createDefaultContainer();
    }

    private void createDefaultEmployee() {
        BranchOffice branchOffice = ControllerCore.getInstance().findBranchOfficeById(1);
        User userAux = new User("employee@gmail.com","123", Set.of(RoleApp.ROLE_EMPLOYEE),branchOffice);
        if(branchOffice !=null){
            if(ControllerCore.getInstance().findUserByEmail("employee@gmail.com") == null){
                ControllerCore.getInstance().createUser(userAux);
            }
        }
        String identificationCard = "111-1111111-1";
        Person personAdmin = new Person(identificationCard,"Yehudy","Rodriguez",new Date(),new Address("San francisco","Calle 20 de Sep #20"),userAux);
        if(ControllerCore.getInstance().findPersonByIdentificationCard(identificationCard) == null){
            ControllerCore.getInstance().createPerson(personAdmin);
        }
    }
    private void createDefaultAdmin() {
        User userAux = new User("admin@gmail.com","123", Set.of(RoleApp.ROLE_ADMIN));
        if(ControllerCore.getInstance().findUserByEmail("admin@gmail.com") == null){
            ControllerCore.getInstance().createUser(userAux);
        }
        String identificationCard = "000-0000000-0";
        Person personAdmin = new Person(identificationCard,"Samuel","Moronta",new Date(),new Address("Santiago","Calle 16 de Agosto #10"),userAux);
        if(ControllerCore.getInstance().findPersonByIdentificationCard(identificationCard) == null){
            ControllerCore.getInstance().createPerson(personAdmin);
        }
    }
    public void createDefaultShelf(){
        BranchOffice branchOffice1 = ControllerCore.getInstance().findBranchOfficeById(1);
        BranchOffice branchOffice2 = ControllerCore.getInstance().findBranchOfficeById(2);
        BranchOffice branchOffice3 = ControllerCore.getInstance().findBranchOfficeById(3);
        BranchOffice branchOffice4 = ControllerCore.getInstance().findBranchOfficeById(4);

        if(ControllerCore.getInstance().findShelfByDeviceId("1") == null){
            Shelf shelfAux1 = new Shelf(branchOffice1);
            ControllerCore.getInstance().addShelf(shelfAux1);
        }
        if(ControllerCore.getInstance().findShelfByDeviceId("2") == null){
            Shelf shelfAux2 = new Shelf(branchOffice2);
            ControllerCore.getInstance().addShelf(shelfAux2);
        }
        if(ControllerCore.getInstance().findShelfByDeviceId("3") == null){
            Shelf shelfAux3 = new Shelf(branchOffice3);
            ControllerCore.getInstance().addShelf(shelfAux3);
        }
        if(ControllerCore.getInstance().findShelfByDeviceId("4") == null){
            Shelf shelfAux4 = new Shelf(branchOffice4);
            ControllerCore.getInstance().addShelf(shelfAux4);
        }
    }
    public void createDefaultContainer(){
        BranchOffice branchOffice = ControllerCore.getInstance().findBranchOfficeById(1);
        Container container = new Container(branchOffice);
        ControllerCore.getInstance().addContainer(container);
    }
    public void createDefaultContainerData(){
       // Container containerAux = ControllerCore.getInstance().findContainerById(1);
      //  if(containerAux!=null){
          //  Container container = new Container(new Date());
           // ControllerCore.getInstance().createContainer(container);
            /*
            int index = 0;
            while(index < 2){
                float weight_data_in = (float) Math.floor(Math.random() * (5.00 + 0.00));
                float MAX_WEIGHT = 5;
                float weight = 0;
                int status_code = 0;

                if(weight_data_in > 0){
                    weight = weight_data_in;

                    if(weight <= MAX_WEIGHT/3){
                        status_code = 1;
                    }else if (weight > MAX_WEIGHT/3 && weight < (MAX_WEIGHT/3) * 2){
                        status_code = 2;
                    }else{
                        if(weight >= MAX_WEIGHT*2 && weight <= MAX_WEIGHT)
                            status_code = 3;
                    }
                }else if (weight_data_in == 0){
                    Notification notification = new Notification("Safacon","El safacon se encuentra toalmente vacio",null);
                    //NotificationServices.getInstance().create(notification);
                    status_code = 4;
                }
                else {
                    System.out.println("Error");
                }

                ContainerData containerData = new ContainerData(
                        weight,
                        ControllerCore.getInstance().findContainerById(1),
                        status_code
                );
                ControllerCore.getInstance().createContainerData(containerData);
                index++;


            }

             */

       // }
    }

}
