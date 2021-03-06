package WEBAPP_SFK.utilities;


import WEBAPP_SFK.controllers.ControllerCore;
import WEBAPP_SFK.models.*;
import WEBAPP_SFK.models.enums.NotificationStatus;
import WEBAPP_SFK.models.enums.RoleApp;
import WEBAPP_SFK.services.*;

import javax.persistence.PersistenceException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DefaultDataLoader {
    private static DefaultDataLoader instance;


    public DefaultDataLoader() {

    }

    public boolean addUserCredential(User user) {
        return UserServices.getInstance().create(user);
    }

    public static DefaultDataLoader getInstance() {
        if (instance == null) {
            instance = new DefaultDataLoader();
        }
        return instance;
    }

    public void createDefaultData() throws ParseException {
        //Company
        createDefaultCompany();
        try {
            createDefaultBranchOffice();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        try {
            createDefaultUsers();
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        addCompanyAndBranchOfficeToUsers();
        createDefaultShelf();
        createDefaultContainer();
        createDefaultWasteData();
        createDefaultFruitProducts();
        createClient();
        createDefaultNotification();
    }

    public void createDefaultCompany() {
        String companyName = "El Nacional";
        String rnc = "001";
        Company company = ControllerCore.getInstance().findCompanyByName(rnc);
        if (company == null) {
            company = new Company(companyName,rnc);
            ControllerCore.getInstance().createCompany(company);
        } else {
            System.out.println("Company wasn't created");
        }
    }

    public void createDefaultBranchOffice() {
        ControllerCore controllerCore1 = new ControllerCore();
        BranchOfficeServices.getInstance().createBranchOffice(new Address("Navarrete", "Calle Barrio Duarte"), controllerCore1.findCompanyByName("001"));
        BranchOfficeServices.getInstance().createBranchOffice(new Address("Santo Domingo Oeste", "Timotes"), controllerCore1.findCompanyByName("001"));
        BranchOfficeServices.getInstance().createBranchOffice(new Address("Boca Chica", "Calle 24"), controllerCore1.findCompanyByName("001"));
    }

    public void createDefaultUsers() {
        User userRoot = ControllerCore.getInstance().findUserByEmail("root@gmail.com");
        if (userRoot == null) {
            userRoot = new User("root@gmail.com", "123", Set.of(RoleApp.ROLE_ROOT));
            ControllerCore.getInstance().createUser(userRoot);
        } else {
            ControllerCore.getInstance().updateUser(userRoot);
        }
        User userEmployee1 = ControllerCore.getInstance().findUserByEmail("yehudyr09@gmail.com");
        if (userEmployee1 == null) {
            userEmployee1 = new User();
            userEmployee1.setEmail("employee1@gmail.com");
            userEmployee1.setPassword("123");
            userEmployee1.setRolesList(Set.of(RoleApp.ROLE_EMPLOYEE));
            ControllerCore.getInstance().createUser(userEmployee1);
        } else {
            ControllerCore.getInstance().updateUser(userEmployee1);
        }
        User userEmployee2 = ControllerCore.getInstance().findUserByEmail("employee2@gmail.com");
        if (userEmployee2 == null) {
            userEmployee2 = new User();
            userEmployee2.setEmail("employee2@gmail.com");
            userEmployee2.setPassword("123");
            userEmployee2.setRolesList(Set.of(RoleApp.ROLE_EMPLOYEE));
            ControllerCore.getInstance().createUser(userEmployee2);
        } else {
            ControllerCore.getInstance().updateUser(userEmployee2);
        }
        User userAdmin = ControllerCore.getInstance().findUserByEmail("admin@gmail.com");
        if (userAdmin == null) {
            userAdmin = new User();
            userAdmin.setEmail("admin@gmail.com");
            userAdmin.setPassword("123");
            userAdmin.setRolesList(Set.of(RoleApp.ROLE_ADMIN));
            ControllerCore.getInstance().createUser(userAdmin);
        } else {
            ControllerCore.getInstance().updateUser(userAdmin);
        }
        Person personEmployee1 = ControllerCore.getInstance().findPersonByIdentificationCard("222-2222222-2");
        if (personEmployee1 == null) {
            personEmployee1 = new Person("222-2222222-2", "Yehudy", "De Pena Rodriguez", new Date(), new Address("San Francisco", "Calle 30 de Marzo #10"), userEmployee1);
            ControllerCore.getInstance().createPerson(personEmployee1);
        } else {
            ControllerCore.getInstance().updatePerson(personEmployee1);
        }
        Person personEmployee2 = ControllerCore.getInstance().findPersonByIdentificationCard("333-3333333-3");
        if (personEmployee2 == null) {
            personEmployee2 = new Person("333-3333333-3", "Armando", "Rodriguez", new Date(), new Address("Santiago", "Calle 27 de Febrero #45"), userEmployee2);
            ControllerCore.getInstance().createPerson(personEmployee2);
        }
        Person personAdmin = ControllerCore.getInstance().findPersonByIdentificationCard("111-1111111-1");
        if (personAdmin == null) {
            personAdmin = new Person("111-1111111-1", "Juan", "Arnaldo", new Date(), new Address("Santiago", "Calle Juan Francisco #16"), userAdmin);
            ControllerCore.getInstance().createPerson(personAdmin);
        }
    }

    public void addCompanyAndBranchOfficeToUsers() {
        Company company = ControllerCore.getInstance().findCompanyByName("001");
        BranchOffice branchOffice1 = ControllerCore.getInstance().findBranchOfficeById(1);
        BranchOffice branchOffice2 = ControllerCore.getInstance().findBranchOfficeById(2);

        User userAdmin = ControllerCore.getInstance().findUserByEmail("admin@gmail.com");
        User userEmployee1 = ControllerCore.getInstance().findUserByEmail("employee1@gmail.com");
        User userEmployee2 = ControllerCore.getInstance().findUserByEmail("employee2@gmail.com");

        if (company != null) {
            if (userAdmin != null) {
                userAdmin.setCompany(company);
                ControllerCore.getInstance().updateUser(userAdmin);
            }
            if (branchOffice1 != null) {
                if (userEmployee1 != null) {
                    userEmployee1.setCompany(company);
                    userEmployee1.setBranchOffice(branchOffice1);
                    ControllerCore.getInstance().updateUser(userEmployee1);
                }
            }
            if (branchOffice2 != null) {
                if (userEmployee2 != null) {
                    userEmployee2.setCompany(company);
                    userEmployee2.setBranchOffice(branchOffice2);
                    ControllerCore.getInstance().updateUser(userEmployee2);
                }
            }
        }
    }

    private void createDefaultWasteData() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh");
        String dateString1 = "30/03/2022 8";
        String dateString2 = "30/03/2022 10";
        String dateString3 = "30/03/2022 14";
        String dateString4 = "30/03/2022 17";

        Date date1 = dateFormat.parse(dateString1);
        Date date2 = dateFormat.parse(dateString2);
        Date date3 = dateFormat.parse(dateString3);
        Date date4 = dateFormat.parse(dateString4);


        WasteData wasteData1 = new WasteData(8.3F, date1, ControllerCore.getInstance().findContainerById("1"));
        WasteData wasteData2 = new WasteData(12.2F, date2, ControllerCore.getInstance().findContainerById("1"));
        WasteData wasteData3 = new WasteData(5.1F, date3, ControllerCore.getInstance().findContainerById("1"));
        WasteData wasteData4 = new WasteData(17.9F, date4, ControllerCore.getInstance().findContainerById("1"));

        WasteDataServices.getInstance().create(wasteData1);
        WasteDataServices.getInstance().create(wasteData2);
        WasteDataServices.getInstance().create(wasteData3);
        WasteDataServices.getInstance().create(wasteData4);

    }

    private void createDefaultAdmin() {
        ControllerCore controllerCore1 = new ControllerCore();
        User userAux = new User("admin@gmail.com", "123", Set.of(RoleApp.ROLE_ADMIN), controllerCore1.findCompanyByName("001"));
        if (controllerCore1.findUserByEmail("admin@gmail.com") == null) {
            ControllerCore.getInstance().createUser(userAux);
        } else {
            System.out.println("This user already exist");
        }
        String identificationCard = "000-0000000-0";
        Person personAdmin = new Person(identificationCard, "Samuel", "Moronta", new Date(), new Address("Santiago", "Calle 16 de Agosto #10"), userAux);
        if (controllerCore1.findPersonByIdentificationCard(identificationCard) == null) {
            controllerCore1.createPerson(personAdmin);
        } else {
            System.out.println("This person already exist");
        }
    }

    public void createDefaultShelf() {
        BranchOffice branchOffice1 = ControllerCore.getInstance().findBranchOfficeById(1);
        BranchOffice branchOffice2 = ControllerCore.getInstance().findBranchOfficeById(2);
        BranchOffice branchOffice3 = ControllerCore.getInstance().findBranchOfficeById(3);

        if (ControllerCore.getInstance().findShelfByDeviceId("1") == null) {
            Shelf shelfAux1 = new Shelf(branchOffice1);
            ControllerCore.getInstance().addShelf(shelfAux1);
        }
        if (ControllerCore.getInstance().findShelfByDeviceId("2") == null) {
            Shelf shelfAux2 = new Shelf(branchOffice2);
            ControllerCore.getInstance().addShelf(shelfAux2);
        }
        if (ControllerCore.getInstance().findShelfByDeviceId("3") == null) {
            Shelf shelfAux3 = new Shelf(branchOffice3);
            ControllerCore.getInstance().addShelf(shelfAux3);
        }
        if (ControllerCore.getInstance().findShelfByDeviceId("4") == null) {
            Shelf shelfAux4 = new Shelf(branchOffice1);
            ControllerCore.getInstance().addShelf(shelfAux4);
        }
    }

    public void createDefaultContainer() {
        BranchOffice branchOffice = ControllerCore.getInstance().findBranchOfficeById(2);
        if (ControllerCore.getInstance().findContainerById("") == null) {
            Container container = new Container(branchOffice);
            ControllerCore.getInstance().addContainer(container);
        }
    }

    public void createDefaultFruitProducts() {
        List<Company> companyList = CompanyServices.getInstance().findAll();


        FruitProduct fp1 = FruitProductServices.getInstance().findProductByName("Pi\\u00f1a");
        FruitProduct fp2 = FruitProductServices.getInstance().findProductByName("Lechosa");

        for (Company c : companyList) {
            if (fp1 == null && fp2 == null) {
                fp1 = new FruitProduct("Pina",10, new Date(), c);
                fp2 = new FruitProduct("Lechosa", 5,new Date(), c);
                FruitProductServices.getInstance().create(fp1);
                FruitProductServices.getInstance().create(fp2);
            }
        }
    }
    public void createDefaultNotification(){
        //    public Notification(String title, String description, Date sendDate, User user, BranchOffice branchOffice, Company company, int type) {
        BranchOffice branchOffice1 = ControllerCore.getInstance().findBranchOfficeById(1);
        User userEmployee1 = ControllerCore.getInstance().findUserByEmail("employee1@gmail.com");
        //    public ShelfData(Float temperature, Float humidity, int fruitCant, String fruitType, int cantOverripe, int cantRipe, int cantUnripe, Shelf shelf) {
        ShelfData s = new ShelfData(30.29F,45.71f,2,"pineapple",1,1,0,ControllerCore.getInstance().findShelfByDeviceId("1"));
        ShelfData s2 = new ShelfData(20.29F,30.71f,3,"papaya",2,0,1,ControllerCore.getInstance().findShelfByDeviceId("1"));
        ShelfDataServices.getInstance().create(s);
        Notification notification = new Notification("MADUREZ",NotificationStatus.MADUREZ.getMessage(), new Date(), userEmployee1,branchOffice1,branchOffice1.getCompany(),1,s,false);
        Notification notification3 = new Notification("TEMPERATURA", NotificationStatus.TEMPERATURA.getMessage(), new Date(), userEmployee1,branchOffice1,branchOffice1.getCompany(),3,false);
        boolean checkOverripe = new ControllerCore().findNotificationByActive(false,1,userEmployee1);
        //En revision = false
        //Revisado = true

        if(checkOverripe == true){
            System.out.println("[MADUREZ ESTA EN PROCESO DE REVISION]");
        }else{
            NotificationServices.getInstance().create(notification);
        }
        if(checkOverripe == true){
            System.out.println("[MADUREZ ESTA EN PROCESO DE REVISION]");
        }else{
            NotificationServices.getInstance().create(notification3);
        }
    }

    public void createClient() {
        Client client = new Client();

        if(ClientServices.getInstance().findClientByEmail("samuelmoronta2@gmail.com") ==null){
            client.setEmail("samuelmoronta2@gmail.com");
            client.setFirstName("Samuel");
            client.setLastName("Moronta");
            ClientServices.getInstance().create(client);
        }
    }

    public void createDefaultContainerData() {
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
