package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.*;
import WEBAPP_SFK.models.enums.RoleApp;
import WEBAPP_SFK.services.*;
import io.javalin.Javalin;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class MainController extends BaseController{

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
        app.routes(() ->{
            get("/logout", ctx -> {
                ctx.clearCookieStore();
                String id = ctx.req.getSession().getId();
                ctx.req.getSession().invalidate();
                ctx.sessionAttribute("user", null);
                ctx.result(String.format("Invalid session: [%s]",id));
                ctx.redirect("/login");
            });
            path("/",() ->{
                /*-------------------------Welcome portal------------------------------------------*/
                get("/", ctx -> ctx.redirect("/portal"));
                /* To get back on portal page (landingPage) */
                post("/portal", ctx -> {
                    ctx.redirect("public/FrontEnd_SFK/views/welcomePortal/portal.html");
                });
                get("/portal", ctx -> {
                    ctx.render("public/FrontEnd_SFK/views/welcomePortal/portal.html",model);
                });
                /*---------------------Company register or login----------------------------*/
                /* To register a new company */
                post("/organizationRegister", ctx ->{
                    String identificationCard = ctx.formParam("identificationCard");
                    String firstName = ctx.formParam("firstName");
                    String lastName = ctx.formParam("lastName");
                    String companyName = ctx.formParam("companyName");
                    String city = ctx.formParam("city");
                    String direction = ctx.formParam("direction");
                    String email = ctx.formParam("email");
                    String password = ctx.formParam("password");

                    if(!email.equals("") && !password.equals("")){
                        User userAux = new User(email,password,Set.of(RoleApp.ROLE_ADMIN));
                        System.out.println(email + " " + password);
                        User userId = ControllerCore.getInstance().findUserByEmail(email);

                        if(userId == null) {
                            ControllerCore.getInstance().createUser(userAux);
                        }else{
                            model.put("ErrorAuth","Este correo electronico ya esta registrado en nuestro sistema");
                        }
                        if(!identificationCard.equals("") && !firstName.equals("") && !lastName.equals("") && !city.equals("") && !direction.equals("")){
                            Person personAux = new Person(identificationCard,firstName,lastName, new Date(),new Address(city,direction),ControllerCore.getInstance().findUserByEmail(email));
                            Company company = new Company(companyName);
                            if(ControllerCore.getInstance().findPersonByIdentificationCard(identificationCard) == null){
                                ControllerCore.getInstance().createPerson(personAux);
                                ControllerCore.getInstance().createCompany(company);
                            }else{
                                model.put("IdentificationCardExist","Esta cedula ya se encuerntra registrada");
                            }
                        }
                        if(ControllerCore.getInstance().findCompanyByName(companyName) !=null){
                            userAux.setCompany(ControllerCore.getInstance().findCompanyByName(companyName));
                            ControllerCore.getInstance().updateUser(userAux);
                        }
                    }
                    ctx.render("/public/FrontEnd_SFK/views/welcomePortal/login.html",model);
                });
                post("/login",ctx -> {
                    String email = ctx.formParam("email");
                    String password = ctx.formParam("password");
                    String rememberMe = ctx.formParam("remember");

                    User userAux = UserServices.getInstance().loginRequest(email,password);

                    if(userAux !=null){
                        if(rememberMe !=null){
                            if(rememberMe.equalsIgnoreCase("ON")){
                                System.out.println("Creando cookie...\n");
                                encryptor.setPassword(mpCryptoPassword);
                                encryptor.encrypt(userAux.getPassword());
                                ctx.cookie("user", userAux.getEmail(),604800);
                                ctx.cookie("password",encryptor.encrypt(userAux.getPassword()),604800);
                            }
                        }else{
                            System.out.println("Cookie no pudo ser creada...\n");
                        }
                        if(userAux.hasRole(RoleApp.ROLE_ADMIN)){
                            ctx.redirect("/management/dashboard");
                        }
                        if(userAux.hasRole(RoleApp.ROLE_ROOT)){
                            ctx.redirect("/projectAdmin");
                        }
                        if(userAux.hasRole(RoleApp.ROLE_EMPLOYEE)){
                            ctx.redirect("/employeePortal");
                        }
                        ctx.sessionAttribute("user", email);
                    }else{
                        ctx.render("/public/FrontEnd_SFK/views/welcomePortal/login.html",model);
                    }
                });
                get("/login",ctx -> {
                    ctx.render("public/FrontEnd_SFK/views/welcomePortal/login.html");
                });
                get("/organizationRegister", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/welcomePortal/companyRegister.html",model);
                });
            });
            path("/management",() ->{
                before("/*",ctx -> {
                    if(ctx.sessionAttribute("user")==null){
                        ctx.redirect("/login");
                    }
                });

                /*--------------------------Dashboard--------------------------------------------*/
                /*Dashboard init page when administrator get started*/
                post("/dashboard", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/dashboard.html");
                });
                get("/dashboard", ctx ->{
                    String email = UserServices.getInstance().find(ctx.sessionAttribute("user")).getEmail();
                    Person person = ControllerCore.controllerCore.findPersonByEmail(email);
                    String fullNameToShow = person.getFirstName() + " "+ person.getLastName();
                    ctx.sessionAttribute("user",email);
                    model.put("fullNameToShow",fullNameToShow);
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/dashboard.html",model);
                });
                /*-------------------------------------------------------------------------------*/
                /*-------------------- Employee management---------------------------------------*/
                get("/employee", ctx ->{
                    List<Person> personList;
                    personList = new PersonServices().findPersonByRole();
                    model.put("employeeList",personList);
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/employeeList.html",model);
                });
                post("/employee-create",ctx->{

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
                    if(!email.equals("") && !password.equals("")){
                        if(branchOffice1 !=null){
                            User userAux = new User(email,password,Set.of(RoleApp.ROLE_EMPLOYEE),branchOffice1);
                            userAux.setCompany(company);
                            System.out.println(email + " " + password);
                            User userId = ControllerCore.getInstance().findUserByEmail(email);
                            if(userId == null) {
                                ControllerCore.getInstance().createUser(userAux);
                            }else{
                                model.put("ErrorAuth","Este correo electronico ya esta registrado en nuestro sistema");
                            }
                            if(!identificationCard.equals("") && !firstName.equals("") && !lastName.equals("") && !city.equals("") && !direction.equals("") && !branchOffice.equals("")){
                                Person personAux = new Person(identificationCard,firstName,lastName, new Date(),new Address(city,direction),ControllerCore.getInstance().findUserByEmail(email));
                                if(ControllerCore.getInstance().findPersonByIdentificationCard(identificationCard) == null){
                                    ControllerCore.getInstance().createPerson(personAux);
                                }else{
                                    model.put("IdentificationCardExist","Esta cedula ya se encuerntra registrada");
                                }
                            }
                        }
                    }
                    ctx.redirect("/management/employee");
                    //ctx.render("/public/FrontEnd_SFK/views/adminPortal/employeeList.html",model);
                });
                get("/createEmployee", ctx ->{
                    Map<String, Object> model = new HashMap<>();
                    model.put("action", "Registrar empleado");
                    model.put("action_form", "/management/employee-create");
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/employeeRegister.html",model);
                });
                post("/employee-edit/:id", ctx ->{

                    Person person = ControllerCore.getInstance().findPersonById(ctx.pathParam("id",Long.class).get());
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
                    if(user!=null){
                        if(user.hasRole(RoleApp.ROLE_EMPLOYEE)){
                            if(branchOffice1!=null){
                                User aux = new User(emailAux,password, Collections.singleton(RoleApp.ROLE_EMPLOYEE));
                                aux.setCompany(company);
                                aux.setBranchOffice(branchOffice1);
                                ControllerCore.getInstance().updateUser(aux);
                                if(person!=null){
                                    person.setIdentificationCard(identificationCard);
                                    person.setAddress(new Address(city,direction));
                                    person.setFirstName(firstName);
                                    person.setLastName(lastName);
                                    ControllerCore.getInstance().updatePerson(person);
                                }else{
                                    ctx.redirect("/management/employee");
                                }
                            }
                        }
                    }
                    ctx.redirect("/management/employee");
                    //ctx.render("/public/FrontEnd_SFK/views/adminPortal/employeeList.html",model);
                });
                get("/employee-edit/:id", ctx ->{
                    Person person = ControllerCore.getInstance().findPersonById(ctx.pathParam("id",Long.class).get());
                    model.put("action", "Editar empleado");
                    if(person.getId() == null){
                        ctx.redirect("/management/employee");
                    }
                    model.put("action_form", "/management/employee-edit/"+person.getId());
                    model.put("person",person);
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/employeeRegister.html",model);
                });

                post("/employeeList", ctx ->{

                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/employeeList.html");
                });
                /*-----------------------------------------------------------------------------*/
                /*---------------------Branch office management--------------------------------*/
                post("/branchOffice", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/branchOffice.html");
                });
                get("/branchOffice", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/branchOffice.html",model);
                });
                /*---------------------Notifications management------------------------------*/
                post("/notification", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/notification.html");
                });
                get("/notification", ctx ->{
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));

                    //Whose's my employees
                    //First find company by branchOffice
                    Company company = user.getCompany();
                    //I know the company branchoffice belongs to
                    //Find user by company
                    model.put("notificationList",company.getNotificationList());

                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/notification.html",model);
                });
                get("/deleteNotificationById/:id", ctx ->{
                    Notification notification = NotificationServices.getInstance().find(ctx.pathParam("id",Long.class).get());
                    NotificationServices.getInstance().delete(notification.getId());
                    ctx.redirect("/management/notification");
                });
                get("/getNotificationByType/:type", ctx ->{
                    Notification notification = NotificationServices.getInstance().find(ctx.pathParam("type",Long.class).get());
                    ctx.redirect("/management/shelf");
                });
                get("/notificationByType-employee/:type", ctx ->{
                    ctx.redirect("/employeePortal/shelfMonitoringEmployee");
                });
                /*---------------------------------------------------------------------------*/
                /*--------------------------Trueque management------------------------------*/
                post("/trueque", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/welcomePortal/trueque.html");
                });
                get("/trueque", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/welcomePortal/trueque.html",model);
                });
                /*---------------------------------------------------------------------------*/
                /*--------------------------Shelf------------------------------*/
                post("/shelfRegister", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/shelfMonitoring.html");
                });
                get("/shelf", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/shelfMonitoring.html",model);
                });
                /*---------------------------------------------------------------------------*/
                /*--------------------------Container------------------------------*/
                post("/container", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/containerMonitoring.html");
                });
                get("/container", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/adminPortal/containerMonitoring.html",model);
                });
                /*---------------------------------------------------------------------------*/
            });
            path("/employeePortal",() ->{
                before("/*",ctx -> {
                    if(ctx.sessionAttribute("user")==null){
                        ctx.redirect("/login");
                    }
                });
                get("/", ctx ->{
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    Person person = ControllerCore.controllerCore.findPersonByEmail(user.getEmail());
                    //String fullNameToShow = person.getFirstName() + " "+ person.getLastName();
                    ctx.sessionAttribute("user",user.getEmail());
                    //model.put("fullNameToShow",fullNameToShow);

                    Company company = user.getCompany();
                    model.put("notificationListEmployee",user.getNotificationList());
                    ctx.render("/public/FrontEnd_SFK/views/employeePortal/employeePortal.html",model);
                });
                get("/shelfMonitoringEmployee", ctx ->{
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));

                    String email = user.getEmail();
                    System.out.println("Email: "+email);
                    if(user.hasRole(RoleApp.ROLE_EMPLOYEE)){
                        BranchOffice branchOffice = BranchOfficeServices.getInstance().findBranchOfficeByUserEmployee(email);
                        if(branchOffice !=null){
                            System.out.println("Info branchOffice"+ branchOffice.getCompany().getName());
                            String nameBranchOffice = branchOffice.getAddress().getCity() + " "+ "(" + branchOffice.getAddress().getDirection()+")";
                            List<Shelf> shelfList;
                            shelfList = new ShelfServices().findShelfByBranchOffice(branchOffice.getId());
                            model.put("branchOfficeSelect",nameBranchOffice);
                            model.put("shelfSelect",shelfList);
                        }
                    }
                    ctx.render("/public/FrontEnd_SFK/views/employeePortal/shelfMonitoringEmployee.html",model);
                });
                get("/containerMonitoringEmployee", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/employeePortal/containerMonitoringEmployee.html",model);
                });
            });
        });
    }
}
