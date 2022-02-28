package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.BranchOffice;
import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.services.BranchOfficeServices;
import WEBAPP_SFK.services.CompanyServices;
import WEBAPP_SFK.services.ContainerServices;
import WEBAPP_SFK.services.ShelfServices;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.get;

public class SfkProjectAdmin extends BaseController{
    private Map<String, Object> model = new HashMap<>();


    public SfkProjectAdmin(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {

        //Welcome
        app.routes(() -> {
            path("/projectAdmin", () -> {
                post("/", ctx ->{
                    model.put("allCompany",CompanyServices.getInstance().findAll().size());
                    model.put("allBranchOffice",BranchOfficeServices.getInstance().findAll().size());
                    model.put("allShelf", ShelfServices.getInstance().findAll().size());
                    model.put("allContainer", ContainerServices.getInstance().findAll().size());
                    ctx.render("/public/FrontEnd_SFK/views/branchOfficeAdmin.html");
                });
                get("/", ctx ->{
                    model.put("allCompany",CompanyServices.getInstance().findAll().size());
                    model.put("allBranchOffice",BranchOfficeServices.getInstance().findAll().size());
                    model.put("allShelf", ShelfServices.getInstance().findAll().size());
                    model.put("allContainer", ContainerServices.getInstance().findAll().size());
                    ctx.render("/public/FrontEnd_SFK/views/branchOfficeAdmin.html",model);
                });
                post("/shelfManagement", ctx ->{
                    String branchOfficeShelf = ctx.formParam("branchOfficeShelf");
                    System.out.println("Branch office: "+branchOfficeShelf);
                    BranchOffice branchOffice = null;
                    if(branchOfficeShelf !=null){
                        branchOffice = ControllerCore.getInstance().findBranchOfficeById(Long.parseLong(branchOfficeShelf));
                    }
                    if(branchOffice !=null){
                        Shelf shelf = new Shelf(branchOffice);
                        ControllerCore.getInstance().addShelf(shelf);
                    }
                    ctx.render("/public/FrontEnd_SFK/views/shelfManagement.html",model);

                });
                get("/shelfManagement", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/shelfManagement.html",model);
                });
                post("/containerManagement", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/containerManagement.html");
                });
                get("/containerManagement", ctx ->{
                    ctx.render("/public/FrontEnd_SFK/views/containerManagement.html",model);
                });
            });

        });
    }
}
