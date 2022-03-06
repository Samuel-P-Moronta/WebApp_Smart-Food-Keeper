package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.*;
import WEBAPP_SFK.models.enums.RoleApp;
import WEBAPP_SFK.services.UserServices;
import io.javalin.Javalin;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class MainController extends BaseController{

    /* Main controller for manage template and request */

    private Map<String, Object> model = new HashMap<>();

    public MainController(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        //Welcome
        app.routes(() ->{
            path("/",() ->{
                /*-------------------------Welcome portal------------------------------------------*/
                get("/", ctx -> ctx.redirect("/portal"));
                /* To get back on portal page (landingPage) */
                post("/portal", ctx -> {
                    ctx.redirect("public/FrontEnd_SFK/views/portal.html");
                });
                get("/portal", ctx -> {
                    ctx.render("public/FrontEnd_SFK/views/portal.html",model);
                });
                /*-------------------------------------------------------------------------------*/
                /*---------------------Company login----------------------------------------*/
                post("/company", ctx -> {
                    ctx.render("public/FrontEnd_SFK/views/login.html",model);
                });
                get("/company", ctx -> {
                    ctx.render("public/FrontEnd_SFK/views/login.html");
                });
                /*-------------------------------------------------------------------------------*/
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
                        User userAux = new User(email,password,Set.of(RoleApp.ROLE_ADMIN,RoleApp.ROLE_EMPLOYEE));
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
                                ControllerCore.getInstance().createOrganization(company);
                            }else{
                                model.put("IdentificationCardExist","Esta cedula ya se encuerntra registrada");
                            }
                        }
                    }
                    ctx.render("/public/FrontEnd_SFK/views/login.html",model);
                });
                post("/login",ctx -> {
                    String email = ctx.formParam("email");
                    String password = ctx.formParam("password");
                    User userAux = UserServices.getInstance().loginRequest(email,password);

                    if(userAux !=null){
                        if(userAux.hasRole(RoleApp.ROLE_ADMIN)){
                            ctx.redirect("/management/dashboard");
                        }
                        if(userAux.hasRole(RoleApp.ROLE_ROOT)){
                            ctx.redirect("/projectAdmin");
                        }
                    }else{
                        ctx.render("/public/FrontEnd_SFK/views/login.html",model);
                    }
                });
                post("/employeeRegister",ctx->{

                    String identificationCard = ctx.formParam("identificationCard");
                    String firstName = ctx.formParam("firstName");
                    String lastName = ctx.formParam("lastName");
                    String companyName = ctx.formParam("companyName");
                    String city = ctx.formParam("city");
                    String direction = ctx.formParam("direction");
                    String email = ctx.formParam("email");
                    String password = ctx.formParam("password");
                    String branchOffice = ctx.formParam("branchOffice");
                    System.out.println(identificationCard);

                    BranchOffice branchOffice1 = ControllerCore.getInstance().findBranchOfficeById(Long.parseLong(branchOffice));

                    if(!email.equals("") && !password.equals("")){
                        if(branchOffice1 !=null){
                            User userAux = new User(email,password,Set.of(RoleApp.ROLE_EMPLOYEE),branchOffice1);
                            System.out.println(email + " " + password);
                            User userId = ControllerCore.getInstance().findUserByEmail(email);
                            if(userId == null) {
                                ControllerCore.getInstance().createUser(userAux);
                            }else{
                                model.put("ErrorAuth","Este correo electronico ya esta registrado en nuestro sistema");
                            }
                            if(!identificationCard.equals("") && !firstName.equals("") && !lastName.equals("") && !city.equals("") && !direction.equals("") && !branchOffice.equals("")){
                                Person personAux = new Person(identificationCard,firstName,lastName, new Date(),new Address(city,direction),ControllerCore.getInstance().findUserByEmail(email));
                                Company company = new Company(companyName);
                                if(ControllerCore.getInstance().findPersonByIdentificationCard(identificationCard) == null){
                                    ControllerCore.getInstance().createPerson(personAux);
                                    ControllerCore.getInstance().createOrganization(company);
                                }else{
                                    model.put("IdentificationCardExist","Esta cedula ya se encuerntra registrada");
                                }
                            }
                        }
                    }
                    ctx.render("/employeeRegister",model);
                });
                get("/login",ctx -> {
                    ctx.render("public/FrontEnd_SFK/views/login.html");
                });
                get("/organizationRegister", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/companyRegister.html",model);
                });
            });
            path("/management",() ->{
                /*--------------------------Dashboard--------------------------------------------*/
                /*Dashboard init page when administrator get started*/
                post("/dashboard", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/dashboard.html");
                });
                get("/dashboard", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/dashboard.html",model);
                });
                /*-------------------------------------------------------------------------------*/
                /*-------------------- Employee management---------------------------------------*/
                post("/employee", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/employee.html");
                });
                get("/employee", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/employee.html",model);
                });
                post("/employeeList", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/employeeList.html");
                });
                get("/employeeList", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/employeeList.html",model);
                });
                /*-----------------------------------------------------------------------------*/
                /*---------------------Branch office management--------------------------------*/
                post("/branchOffice", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/branchOffice.html");
                });
                get("/branchOffice", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/branchOffice.html",model);
                });
                /*-----------------------------------------------------------------------------*/
                /*-----------------------------Container management----------------------------*/
                post("/containerMgmt", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/containerMgmt.html");
                });
                get("/containerMgmt", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/containerMgmt.html",model);
                });
                /*---------------------------------------------------------------------------*/
                /*---------------------Notifications management------------------------------*/
                post("/notification", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/notification.html");
                });
                get("/notification", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/notification.html",model);
                });
                /*---------------------------------------------------------------------------*/
                /*--------------------------Trueque management------------------------------*/
                post("/trueque", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/trueque.html");
                });
                get("/trueque", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/trueque.html",model);
                });
                /*---------------------------------------------------------------------------*/
                /*--------------------------Shelf------------------------------*/
                post("/shelfRegister", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/shelfMonitoring.html");
                });
                get("/shelf", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/shelfMonitoring.html",model);
                });
                /*---------------------------------------------------------------------------*/
                /*--------------------------Container------------------------------*/
                post("/container", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/containerMonitoring.html");
                });
                get("/container", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/containerMonitoring.html",model);
                });
                /*---------------------------------------------------------------------------*/
            });

            //
        });

    }
}
