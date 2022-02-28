package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.BranchOffice;
import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.services.BranchOfficeServices;
import WEBAPP_SFK.services.CompanyServices;
import WEBAPP_SFK.services.ContainerServices;
import WEBAPP_SFK.services.ShelfServices;
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
                    ctx.render("/public/FrontEnd_SFK/views/shelf.html");
                });
                get("/shelf", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/shelf.html",model);
                });
                /*---------------------------------------------------------------------------*/
                /*--------------------------Container------------------------------*/
                post("/container", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/container.html");
                });
                get("/container", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/container.html",model);
                });
                /*---------------------------------------------------------------------------*/
            });

            //
        });

    }
}
