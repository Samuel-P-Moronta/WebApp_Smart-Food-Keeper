package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.*;
import WEBAPP_SFK.models.ExpressSalesInventory;
import WEBAPP_SFK.models.enums.RoleApp;
import WEBAPP_SFK.services.*;
import WEBAPP_SFK.utilities.EmailSender;
import io.javalin.Javalin;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class MainController extends BaseController {

    /* Main controller for manage template and request */
    private static String mpCryptoPassword = "BornToFight";
    private Map<String, Object> model = new HashMap<>();
    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();


    public MainController(Javalin app) {
        super(app);
        encryptor = new StandardPBEStringEncryptor();
    }

    @Override
    public void aplicarRutas() {
        //Welcome
        app.routes(() -> {
            get("/logout", ctx -> {
                ctx.clearCookieStore();
                String id = ctx.req.getSession().getId();
                ctx.req.getSession().invalidate();
                ctx.sessionAttribute("user", null);
                ctx.result(String.format("Invalid session: [%s]", id));
                ctx.redirect("/login");
            });
            path("/", () -> {
                /*-------------------------Welcome portal------------------------------------------*/
                get("/", ctx -> ctx.redirect("/portal"));
                /* To get back on portal page (landingPage) */
                post("/portal", ctx -> {
                    System.out.println("Estoy en portal enpoint");
                    String firstName = ctx.formParam("firstName");
                    String lastName = ctx.formParam("lastName");
                    String email = ctx.formParam("email");
                    if(!(firstName.equals("") && lastName.equals("") && email.equals(""))){
                        if(ClientServices.getInstance().findClientByEmail(email) == null && PersonServices.getInstance().findPersonByEmail(email) == null){
                            Client client = new Client();
                            client.setFirstName(firstName);
                            client.setLastName(lastName);
                            client.setEmail(email);
                            client.setSubscriptionDate(new Date());
                            ClientServices.getInstance().create(client);
                        }else{
                            System.out.println("found the same email");
                        }
                    }else{
                        System.out.println("Empty camps");
                    }
                    ctx.render("public/FrontEnd_SFK/views/welcomePortal/portal.html", model);
                });
                get("/portal", ctx -> {
                    ctx.render("public/FrontEnd_SFK/views/welcomePortal/portal.html", model);
                });
                post("/client-subscribe", ctx ->{
                    String firstName = ctx.formParam("firstName");
                    String lastName = ctx.formParam("lastName");
                    String email = ctx.formParam("email");
                    if(!(firstName.equals("") && lastName.equals("") && email.equals(""))){
                        if(ClientServices.getInstance().findClientByEmail(email) !=null && PersonServices.getInstance().findPersonByEmail(email) !=null){
                            Client client = new Client();
                            client.setFirstName(firstName);
                            client.setLastName(lastName);
                            client.setEmail(email);
                            client.setSubscriptionDate(new Date());
                            ClientServices.getInstance().create(client);
                        }
                    }
                    ctx.render("public/FrontEnd_SFK/views/welcomePortal/portal.html", model);
                });
                /*---------------------Company register or login----------------------------*/
                /* To register a new company */
                post("/organizationRegister", ctx -> {
                    String identificationCard = ctx.formParam("identificationCard");
                    String firstName = ctx.formParam("firstName");
                    String lastName = ctx.formParam("lastName");
                    String companyName = ctx.formParam("companyName");
                    String city = ctx.formParam("city");
                    String direction = ctx.formParam("direction");
                    String email = ctx.formParam("email");
                    String password = ctx.formParam("password");

                    String companyNameAux = companyName.toUpperCase();

                    User user = ControllerCore.getInstance().findUserByEmail(email);
                    Company company = ControllerCore.getInstance().findCompanyByName(companyNameAux);
                    Person person = ControllerCore.getInstance().findPersonByIdentificationCard(identificationCard);

                    if (!(identificationCard.equals("") && firstName.equals("") && lastName.equals("") && companyName.equals("") && city.equals("") && direction.equals("") && email.equals("") && password.equals(""))) {
                        if (company == null) {
                            company = new Company(companyNameAux);
                            ControllerCore.getInstance().createCompany(company);
                        }
                        if (user == null) {
                            Company companyAux = ControllerCore.getInstance().findCompanyByName(companyNameAux);
                            user = new User();
                            user.setEmail(email);
                            user.setPassword(password);
                            user.setRolesList(Set.of(RoleApp.ROLE_ADMIN));
                            if (companyAux != null) {
                                user.setCompany(companyAux);
                                ControllerCore.getInstance().createUser(user);
                            }
                            if (person == null) {
                                person = new Person();
                                person.setIdentificationCard(identificationCard);
                                person.setFirstName(firstName);
                                person.setLastName(lastName);
                                person.setRegisterDate(new Date());
                                person.setAddress(new Address(city, direction));
                                person.setUser(user);
                                ControllerCore.getInstance().createPerson(person);
                                System.out.println("Account was created successfully");
                                String successMessage = firstName + " " + lastName + " " + "Se ha registro con exito en nuestro sistema";
                                model.put("successMessage", successMessage);
                                model.put("emailExist", "");
                                model.put("errorIdentificationCard", "");

                            } else {
                                if (ControllerCore.getInstance().findPersonByIdentificationCard(identificationCard) != null) {
                                    model.put("errorIdentificationCard", "Esta cedula ya esta registrado en nuestro sistema");
                                    model.put("emailExist", "");
                                    model.put("successMessage", "");
                                }
                            }
                        } else if (user != null) {
                            System.out.println("This user already exist");
                            model.put("emailExist", "Este correo electronico ya esta registrado en nuestro sistema");
                            model.put("errorIdentificationCard", "");
                            model.put("successMessage", "");
                        }
                    }
                    ctx.render("/public/FrontEnd_SFK/views/welcomePortal/companyRegister.html", model);


                });
                get("/organizationRegister", ctx -> {
                    model.put("errorIdentificationCard", "");
                    model.put("emailExist", "");
                    model.put("successMessage", "");
                    ctx.render("/public/FrontEnd_SFK/views/welcomePortal/companyRegister.html", model);
                });

                post("/login", ctx -> {
                    String email = ctx.formParam("email");
                    String password = ctx.formParam("password");
                    String rememberMe = ctx.formParam("remember");

                    User userAux = UserServices.getInstance().loginRequest(email, password);

                    if (userAux != null) {
                        if (rememberMe != null) {
                            if (rememberMe.equalsIgnoreCase("ON")) {
                                System.out.println("Creando cookie...\n");
                                encryptor.setPassword(mpCryptoPassword);
                                encryptor.encrypt(userAux.getPassword());
                                ctx.cookie("user", userAux.getEmail(), 604800);
                                ctx.cookie("password", encryptor.encrypt(userAux.getPassword()), 604800);
                            }
                        } else {
                            System.out.println("Cookie no pudo ser creada...\n");
                        }
                        if (userAux.hasRole(RoleApp.ROLE_ADMIN)) {
                            ctx.redirect("/management/dashboard");
                        }
                        if (userAux.hasRole(RoleApp.ROLE_ROOT)) {
                            ctx.redirect("/projectAdmin");
                        }
                        if (userAux.hasRole(RoleApp.ROLE_EMPLOYEE)) {
                            ctx.redirect("/employeePortal");
                        }
                        ctx.sessionAttribute("user", email);
                    } else {
                        model.put("ErrorAuth", "Credenciales no validas!");
                        ctx.render("/public/FrontEnd_SFK/views/welcomePortal/login.html", model);
                    }
                });
                get("/login", ctx -> {
                    ctx.render("public/FrontEnd_SFK/views/welcomePortal/login.html");
                });
            });
            path("/management", () -> {
                before("/*", ctx -> {
                    if (ctx.sessionAttribute("user") == null) {
                        ctx.redirect("/login");
                    }
                });

                /*--------------------------Dashboard--------------------------------------------*/
                /*Dashboard init page when administrator get started*/
                post("/dashboard", ctx -> {
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/dashboard.html");
                });
                get("/dashboard", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    String email = UserServices.getInstance().find(ctx.sessionAttribute("user")).getEmail();
                    Person person = ControllerCore.controllerCore.findPersonByEmail(email);
                    String fullNameToShow = person.getFirstName() + " " + person.getLastName();
                    ctx.sessionAttribute("user", email);
                    model.put("fullNameToShow", fullNameToShow.toUpperCase());
                    Company company = user.getCompany();
                    model.put("company", company.getName());
                    System.out.println("NOMBRE COMPANY EN DASHBOARD: " + company.getName());
                    int amountBranchOffice = company.getBranchOfficeList().size();
                    Set<BranchOffice> branchOfficeList;
                    int totalShelf = 0, flagShelg = 0, flagContainer = 0, totalContainer = 0;
                    branchOfficeList = company.getBranchOfficeList();
                    model.put("myBranchOffices", amountBranchOffice);
                    for (BranchOffice list : branchOfficeList) {
                        totalShelf += list.getShelfList().size();
                        totalContainer += list.getContainerList().size();
                    }
                    flagShelg = totalShelf;
                    flagContainer = totalContainer;
                    model.put("myShelves", flagShelg);
                    model.put("myContainers", flagContainer);
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/dashboard.html", model);
                });
                /*-------------------------------------------------------------------------------*/
                /*-------------------- Employee management---------------------------------------*/
                get("/employee", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    List<Person> personList;
                    personList = new PersonServices().findCompanyEmployees(user.getCompany().getId());
                    model.put("employeeList", personList);
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/employeeList.html", model);
                });

                post("/employee-create", ctx -> {

                    String identificationCard = ctx.formParam("identificationCard");
                    System.out.println(identificationCard);
                    String firstName = ctx.formParam("firstName");
                    System.out.println(firstName);
                    String lastName = ctx.formParam("lastName");
                    System.out.println(lastName);
                    String city = ctx.formParam("city");
                    System.out.println(city);
                    String direction = ctx.formParam("direction");
                    System.out.println(direction);
                    String email = ctx.formParam("email");
                    System.out.println(email);
                    String password = ctx.formParam("password");
                    System.out.println(password);
                    String branchOffice = ctx.formParam("idBranchOffice");
                    System.out.println(branchOffice);

                    BranchOffice branchOffice1 = ControllerCore.getInstance().findBranchOfficeById(Long.parseLong(branchOffice));
                    Company company = ControllerCore.getInstance().findCompanyByBranchOffice(branchOffice1.getId());
                    if (!email.equals("") && !password.equals("")) {
                        if (branchOffice1 != null) {
                            User userAux = new User(email, password, Set.of(RoleApp.ROLE_EMPLOYEE), branchOffice1);
                            userAux.setCompany(company);
                            System.out.println(email + " " + password);
                            User userId = ControllerCore.getInstance().findUserByEmail(email);
                            if (userId == null) {
                                ControllerCore.getInstance().createUser(userAux);
                            } else {
                                model.put("ErrorAuth", "Este correo electronico ya esta registrado en nuestro sistema");
                            }
                            if (!identificationCard.equals("") && !firstName.equals("") && !lastName.equals("") && !city.equals("") && !direction.equals("") && !branchOffice.equals("")) {
                                Person personAux = new Person(identificationCard, firstName, lastName, new Date(), new Address(city, direction), ControllerCore.getInstance().findUserByEmail(email));
                                if (ControllerCore.getInstance().findPersonByIdentificationCard(identificationCard) == null) {
                                    ControllerCore.getInstance().createPerson(personAux);
                                } else {
                                    model.put("IdentificationCardExist", "Esta cedula ya se encuerntra registrada");
                                }
                            }
                        }
                    }
                    ctx.redirect("/management/employee");
                    //ctx.render("/public/FrontEnd_SFK/views/adminPortal/employeeList.html",model);
                });
                get("/createEmployee", ctx -> {
                    Map<String, Object> model = new HashMap<>();
                    model.put("action", "Registrar empleado");
                    model.put("action_form", "/management/employee-create");
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/employeeRegister.html", model);
                });
                post("/employee-edit/:id", ctx -> {

                    Person person = ControllerCore.getInstance().findPersonById(ctx.pathParam("id", Long.class).get());
                    User user = person.getUser();
                    String identificationCard = ctx.formParam("identificationCard");
                    String firstName = ctx.formParam("firstName");
                    String lastName = ctx.formParam("lastName");
                    String emailAux = ctx.formParam("email");
                    String city = ctx.formParam("city");
                    String direction = ctx.formParam("direction");
                    String password = ctx.formParam("password");
                    String branchOffice = ctx.formParam("idBranchOffice");
                    BranchOffice branchOffice1 = ControllerCore.getInstance().findBranchOfficeById(Long.parseLong(branchOffice));
                    Company company = ControllerCore.getInstance().findCompanyByBranchOffice(branchOffice1.getId());
                    if (user != null) {
                        if (user.hasRole(RoleApp.ROLE_EMPLOYEE)) {
                            if (branchOffice1 != null) {
                                User aux = new User(emailAux, password, Collections.singleton(RoleApp.ROLE_EMPLOYEE));
                                aux.setCompany(company);
                                aux.setBranchOffice(branchOffice1);
                                ControllerCore.getInstance().updateUser(aux);
                                if (person != null) {
                                    person.setIdentificationCard(identificationCard);
                                    person.setAddress(new Address(city, direction));
                                    person.setFirstName(firstName);
                                    person.setLastName(lastName);
                                    ControllerCore.getInstance().updatePerson(person);
                                } else {
                                    ctx.redirect("/management/employee");
                                }
                            }
                        }
                    }
                    ctx.redirect("/management/employee");
                    //ctx.render("/public/FrontEnd_SFK/views/adminPortal/employeeList.html",model);
                });
                get("/employee-edit/:id", ctx -> {
                    Person person = ControllerCore.getInstance().findPersonById(ctx.pathParam("id", Long.class).get());
                    model.put("action", "Editar empleado");
                    if (person.getId() == null) {
                        ctx.redirect("/management/employee");
                    }
                    model.put("action_form", "/management/employee-edit/" + person.getId());
                    model.put("person", person);
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/employeeRegister.html", model);
                });

                post("/employeeList", ctx -> {

                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/employeeList.html");
                });
                /*-----------------------------------------------------------------------------*/
                /*---------------------Branch office management--------------------------------*/
                post("/branchOffice", ctx -> {
                    ctx.render("/public/FrontEnd_SFK/views/branchOffice.html");
                });
                get("/branchOffice", ctx -> {
                    ctx.render("/public/FrontEnd_SFK/views/branchOffice.html", model);
                });
                /*---------------------Notifications management------------------------------*/
                post("/notification", ctx -> {
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/notification.html");
                });
                get("/notification", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    Company company = user.getCompany();
                    model.put("notificationList", company.getNotificationList());

                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/notification.html", model);
                });
                get("/notification-employee", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    Company company = user.getCompany();
                    model.put("notificationList", company.getNotificationList());

                    ctx.render("/public/FrontEnd_SFK/views/employeePortal/employeePortal.html", model);
                });
                get("/deleteNotificationById/:id", ctx -> {
                    Notification notification = NotificationServices.getInstance().find(ctx.pathParam("id", Long.class).get());
                    NotificationServices.getInstance().delete(notification.getId());
                    ctx.redirect("/management/notification");
                });
                get("/getNotificationByType/:type", ctx -> {
                    Notification notification = NotificationServices.getInstance().find(ctx.pathParam("type", Long.class).get());
                    ctx.redirect("/management/shelf");
                });
                get("/notificationByType-employee/:type", ctx -> {
                    ctx.redirect("/employeePortal/shelfMonitoringEmployee");
                });
                /*---------------------------------------------------------------------------*/
                /*--------------------------Trueque management------------------------------*/
                post("/discount-create", ctx -> {
                    //Definir porcentaje de descuento
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    float discountPercentage = Float.parseFloat(ctx.formParam("discountPercentage"));

                    String tFruit = ctx.formParam("fruitType");
                    Company company = user.getCompany();
                    if(user!=null){
                        FruitProduct fp = new FruitProduct();
                        fp.setCompany(company);
                        fp.setFruitType(tFruit);
                        fp.setDiscountPercentage(discountPercentage);
                        fp.setRegisterDate(new Date());
                        FruitProductServices.getInstance().create(fp);
                    }
                    ctx.redirect("/management/discount");
                });
                post("/discount-edit/:id", ctx -> {
                    FruitProduct fruitProduct = FruitProductServices.getInstance().find(ctx.pathParam("id",Long.class).get());
                    float discountPercentage = Float.parseFloat(ctx.formParam("discountPercentage"));
                    if(fruitProduct!=null){
                        fruitProduct.setDiscountPercentage(discountPercentage);
                        FruitProductServices.getInstance().update(fruitProduct);
                    }
                    else{
                        System.out.println("not found");
                    }
                    ctx.redirect("/management/express");
                });
                get("/discount-edit/:id", ctx -> {
                    FruitProduct fruitProduct = FruitProductServices.getInstance().find(ctx.pathParam("id",Long.class).get());

                    model.put("action", "Editar porcentaje de descuento");
                    if (fruitProduct.getId() == null) {
                        ctx.redirect("/management/discount");
                    }
                    model.put("action_form_discount", "/management/discount-edit/" + fruitProduct.getId());
                    model.put("fruitProduct", fruitProduct);
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/discountPercentageCreate.html", model);
                });
                get("/discount", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    List<FruitProduct> fpList = new FruitProductServices().findAll();
                    Company company = user.getCompany();
                    if(user !=null){
                        if(company!=null){
                            model.put("productListResult",company.getFruitProductList());
                        }
                    }
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/express.html", model);
                });
                get("/discount-createP", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    List<FruitProduct> fpList = new FruitProductServices().findAll();
                    Company company = user.getCompany();
                    if(user !=null){
                        if(company!=null){
                            model.put("productListResult",company.getFruitProductList());
                        }
                    }
                    model.put("action", "Registrar porciento de descuento");
                    model.put("action_form_discount", "/management/discount-create");
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/discountPercentageCreate.html", model);
                });
                get("/express", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    Company company = user.getCompany();
                    if(user !=null){
                        if(company !=null){
                            model.put("fruitList",company.getFruitProductList());
                        }
                    }
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/express.html", model);
                });
                /*---------------------------------------------------------------------------*/
                /*--------------------------Shelf------------------------------*/
                post("/shelfRegister", ctx -> {
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/shelfMonitoring.html");
                });
                get("/shelf", ctx -> {
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/shelfMonitoring.html", model);
                });
                /*---------------------------------------------------------------------------*/
                /*--------------------------Container------------------------------*/
                post("/container", ctx -> {
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/containerMonitoring.html");
                });
                get("/container", ctx -> {
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/containerMonitoring.html", model);
                });
                /*---------------------------------------------------------------------------*/
            });
            before("/employeePortal", ctx -> {
                if (ctx.sessionAttribute("user") == null) {
                    ctx.redirect("/login");
                }
            });
            path("/employeePortal", () -> {
                before("/*", ctx -> {
                    if (ctx.sessionAttribute("user") == null) {
                        ctx.redirect("/login");
                    }
                });
                get("/", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    Person person = ControllerCore.controllerCore.findPersonByEmail(user.getEmail());
                    String fullNameToShow = person.getFirstName() + " "+ person.getLastName();
                    ctx.sessionAttribute("user", user.getEmail());
                    model.put("fullNameToShow",fullNameToShow.toUpperCase());
                    Company company = user.getCompany();
                    model.put("notificationListEmployee", user.getNotificationList());
                    ctx.render("/public/FrontEnd_SFK/views/employeePortal/employeePortal.html", model);
                });
                get("/delete-notification-employee/:id", ctx -> {
                    Notification notification = NotificationServices.getInstance().find(ctx.pathParam("id", Long.class).get());
                    NotificationServices.getInstance().delete(notification.getId());
                    ctx.redirect("/employeePortal");
                });
                get("/shelfMonitoringEmployee", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));

                    String email = user.getEmail();
                    System.out.println("Email: " + email);
                    if (user.hasRole(RoleApp.ROLE_EMPLOYEE)) {
                        BranchOffice branchOffice = BranchOfficeServices.getInstance().findBranchOfficeByUserEmployee(email);
                        if (branchOffice != null) {
                            System.out.println("Info branchOffice" + branchOffice.getCompany().getName());
                            String nameBranchOffice = branchOffice.getAddress().getCity() + " " + "(" + branchOffice.getAddress().getDirection() + ")";
                            List<Shelf> shelfList;
                            shelfList = new ShelfServices().findShelfByBranchOffice(branchOffice.getId());
                            model.put("branchOfficeSelect", nameBranchOffice);
                            model.put("shelfSelect", shelfList);
                            /*
                            model.put("lastFruitType",lastFruitTypeInDb);
                            model.put("lastCantFruit",lastCantFruit);

                             */
                        }
                    }
                    ctx.render("/public/FrontEnd_SFK/views/employeePortal/shelfMonitoringEmployee.html", model);
                });
                get("/containerMonitoringEmployee", ctx -> {
                    ctx.render("/public/FrontEnd_SFK/views/employeePortal/containerMonitoringEmployee.html", model);
                });
                get("/sales-express", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    if (user != null) {
                        String email = user.getEmail();
                        BranchOffice branchOffice = BranchOfficeServices.getInstance().findBranchOfficeByUserEmployee(email);
                        if (branchOffice != null) {
                            String myBranchOffice = branchOffice.getAddress().getCity() + " " + "(" + branchOffice.getAddress().getDirection() + ")";
                            List<Shelf> shelfList;
                            shelfList = new ShelfServices().findShelfByBranchOffice(branchOffice.getId());
                            model.put("branchOfficeEmployee", myBranchOffice);
                            model.put("shelfSelect", shelfList);
                            List<ExpressSalesInventory> eSI = new ExpressSalesInventoryServices().findInspectionsByUser(user.getEmail());
                            model.put("makingPostList",eSI);
                            List<FruitProduct> fp = new FruitProductServices().findAll();
                            model.put("productList",fp);
                        }
                    }
                    ctx.render("/public/FrontEnd_SFK/views/employeePortal/salesExpress.html", model);
                });
                post("/sales-express", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    if (user != null) {
                        if (user.hasRole(RoleApp.ROLE_EMPLOYEE)) {
                            String email = user.getEmail();
                            System.out.println(email);
                            BranchOffice branchOffice = BranchOfficeServices.getInstance().findBranchOfficeByUserEmployee(email);
                            System.out.println(branchOffice.getAddress().getCity());
                            if (branchOffice != null) {
                                ExpressSalesInventory eSalesInventory = new ExpressSalesInventory();
                                int currentQuantity = Integer.parseInt(ctx.formParam("fruitQuantity"));
                                System.out.println(currentQuantity);
                                String fruitType = ctx.formParam("fruitType");
                                System.out.println(fruitType);
                                String idShelfSelect = ctx.formParam("shelfSelect");
                                System.out.println(idShelfSelect);
                                Shelf shelf = ControllerCore.getInstance().findShelfByDeviceId(idShelfSelect);
                                if (shelf != null) {
                                    if(!(fruitType.equals("") && currentQuantity!=0)){
                                        FruitProduct fp = FruitProductServices.getInstance().findProductByName(fruitType);
                                        eSalesInventory.setQuantity(currentQuantity);
                                        eSalesInventory.setInspectionDate(new Date());
                                        eSalesInventory.setUser(user);
                                        eSalesInventory.setBranchOffice(branchOffice);
                                        eSalesInventory.setShelf(shelf);
                                        eSalesInventory.setFruitType(fruitType);
                                        eSalesInventory.setDiscountPercentage(fp.getDiscountPercentage());
                                        ExpressSalesInventoryServices.getInstance().create(eSalesInventory);
                                        //Broadcast clients notifications
                                    }
                                }else{
                                    System.out.println("Shelf not found");
                                }
                            }
                        }
                        List<ExpressSalesInventory> esI = new ExpressSalesInventoryServices().findInspectionsByUser(user.getEmail());
                        model.put("makingPostList",esI);
                    }
                    ctx.render("/public/FrontEnd_SFK/views/employeePortal/salesExpress.html", model);
                });
                post("/publish-express", ctx -> {
                    List<ExpressSalesInventory> esIList = new ExpressSalesInventoryServices().findAll();
                    List<Client> clientList = new ClientServices().findAll();
                    String customSubject = "Frutas express.";
                    for(ExpressSalesInventory e: esIList){
                        for(Client c: clientList){
                            String customMessage = "Hola " + " " + c.getFirstName()+"."+" "+ "Este correo es para informarte tenemos"+" "+e.getFruitType()+"s"+" "+"con un "+e.getDiscountPercentage()+"%" + " "+ "de descuento."+" "+"En el supermercado:"+" "+e.getBranchOffice().getCompany().getName()+" "+ "y sucursal"+" "+e.getBranchOffice().getAddress().getCity() +" "+"("+e.getBranchOffice().getAddress().getDirection()+")";
                            new EmailSender().message(c.getEmail(),customSubject,customMessage);
                        }
                        ExpressSalesInventoryServices.getInstance().delete(e.getId());
                    }
                    ctx.redirect("/employeePortal/sales-express");
                });
            });
        });
    }
}