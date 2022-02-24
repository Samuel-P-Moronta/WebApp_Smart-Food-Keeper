package WEBAPP_SFK.controllers;

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
                /*---------------------Organization login----------------------------------------*/
                post("/organization", ctx -> {
                    ctx.render("public/FrontEnd_SFK/views/login.html",model);
                });
                get("/organization", ctx -> {
                    ctx.render("public/FrontEnd_SFK/views/login.html");
                });
                /*-------------------------------------------------------------------------------*/
                /*---------------------Organization register or login----------------------------*/
                /* To register a new company */
                post("/organizationRegister", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/login.html");
                });
                get("/organizationRegister", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/organizationRegister.html",model);
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
            });

            //
        });

    }
}
