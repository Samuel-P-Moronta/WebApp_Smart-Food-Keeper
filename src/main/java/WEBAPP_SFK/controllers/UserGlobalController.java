package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.Address;
import WEBAPP_SFK.models.Company;
import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.models.User;
import WEBAPP_SFK.models.enums.RoleApp;
import io.javalin.Javalin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class UserGlobalController extends BaseController{
    private Map<String, Object> model = new HashMap<>();


    public UserGlobalController(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        app.routes(()->{
            path("/user",() ->{
                post("/companyRegister",ctx->{
                    String identificationCard = ctx.formParam("identificationCard");
                    String firstName = ctx.formParam("firstName");
                    String lastName = ctx.formParam("lastName");
                    String companyName = ctx.formParam("companyName");
                    String city = ctx.formParam("city");
                    String direction = ctx.formParam("direction");
                    String email = ctx.formParam("email");
                    String password = ctx.formParam("password");

                    if(!email.equals("") && !password.equals("")){
                        User userAux = new User(email,password, Set.of(RoleApp.ROLE_ADMIN,RoleApp.ROLE_EMPLOYEE));
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
                    }
                    ctx.render("/public/FrontEnd_SFK/views/welcomePortal/login.html",model);

                });
            });
        });
    }
}
