package WEBAPP_SFK.utilities;


import WEBAPP_SFK.controllers.ControllerCore;
import WEBAPP_SFK.models.*;
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
    public void createDefaultOrganization(){
        Logger.getInstance().getLog(this.getClass()).info("Creating default organizations [...]");
        Organization organization1 = new Organization("El nacional", new Date());
        Organization organization2 = new Organization("El Bravo", new Date());
        Organization organization3 = new Organization("Caco de botella", new Date());

        ControllerCore.getInstance().createOrganization(organization1);
        ControllerCore.getInstance().createOrganization(organization2);
        ControllerCore.getInstance().createOrganization(organization3);
    }
    public void createDefaultBranchOffice(){
        Logger.getInstance().getLog(this.getClass()).info("Creating default branch offices [...]");
        Address address = new Address("Etrella Sadhala","Santiago" );
        Address address1 = new Address("Calle el bravo XD", "Santiago");
        Address address2 = new Address("Calle 16 de agosto", "Navarrete");

        ControllerCore controllerCore1 = new ControllerCore();
        ControllerCore controllerCore2 = new ControllerCore();
        ControllerCore controllerCore3 = new ControllerCore();

        BranchOffice branchOffice1 = new BranchOffice(address, new Date(),controllerCore1.findOrganizationByName("El nacional"));
        BranchOffice branchOffice2 = new BranchOffice(address1, new Date(),controllerCore1.findOrganizationByName("El Bravo"));
        BranchOffice branchOffice3 = new BranchOffice(address2, new Date(),controllerCore1.findOrganizationByName("Caco de botella"));

        ControllerCore.getInstance().createBranchOffice(branchOffice1);
        ControllerCore.getInstance().createBranchOffice(branchOffice2);
        ControllerCore.getInstance().createBranchOffice(branchOffice3);

    }
    public void createDefaultSuperUser(){
        Logger.getInstance().getLog(this.getClass()).info("Creating default users [...]");
        // Creating organization
        Organization organization = new Organization("La Sirena",new Date());
        // Creating branch Office
        BranchOffice branchOffice = new BranchOffice(new Address("Calle 30 de Marzo","Santiago"),new Date(),ControllerCore.getInstance().findOrganizationById(1));
        //Creating super user
        /*
        User user = new User(
                "sfkproject@gmail.com",
                "admin",
                "admin",
                Collections.singleton(RoleApp.ROLE_ADMIN),
                null,
                ControllerCore.getInstance().findBranchOfficeById(1)
        );
        User user2 = new User(
                "employee@gmail.com",
                "employee",
                "employee",
                Collections.singleton(RoleApp.ROLE_EMPLOYEE),
                null,
                ControllerCore.getInstance().findOrganizationById(1)
        );

        //Creating defulalt person
        Person person = new Person(
                "222-2222222-2",
                "Rafael",
                "Dorville",
                "employee@gmail.com",
                Gender.MALE,
                new Address("Calle 16 de Agosto","Santiago"),
                user2
        );
        Person person2 = new Person(
                "111-1111111-1",
                "Yehudy",
                "De Pena",
                "Yehudy@gmail.com",
                Gender.MALE,
                new Address("Calle 16 de Agosto","San Francisco"),user
        );

        if(ControllerCore.getInstance().findUserByEmail("root@gmail.com") == null){
             ControllerCore.getInstance().createOrganization(organization);
             ControllerCore.getInstance().createBranchOffice(branchOffice);
             ControllerCore.getInstance().createUser(user2);
             ControllerCore.getInstance().createPerson(person);

       }

         */
    }
    public void createDefaultShelfData(){
        //First we have to create default shelf to bring shelf data
        Shelf shelf = new Shelf("SH001",new Date());
        Shelf shelfAux = ControllerCore.getInstance().getShelfByDeviceName("SH001");
        if(shelfAux == null){
            ControllerCore.getInstance().addShelf(shelf);
            int i = 0;


            while (i < 3) {
                float temperature = (float) Math.floor(Math.random() * (40 - 20 + 1) + 20);
                float humidity = (float) Math.floor(Math.random() * (40 - 20 + 1) + 20);
                int cantFrutas = 4;
                int cantOverripe = 1;
                int cantRipe = 2;
                int cantUnripe = 1;
                Date currentSampleDate = new Date(System.currentTimeMillis());
                ShelfData sd = new ShelfData(
                        temperature,
                        humidity, cantFrutas,
                        "PINEAPPLE",
                        cantOverripe,
                        cantRipe,
                        cantUnripe,
                        currentSampleDate,
                        ControllerCore.getInstance().getShelfByDeviceName("SH001"));
                ControllerCore.getInstance().addShelfData(sd);
                ++i;
            }


        }
    }
    public void createDefaultContainerData(){
        Container containerAux = ControllerCore.getInstance().findContainerById(1);
        if(containerAux!=null){
            Container container = new Container(new Date());
            ControllerCore.getInstance().createContainer(container);
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

        }
    }

}
