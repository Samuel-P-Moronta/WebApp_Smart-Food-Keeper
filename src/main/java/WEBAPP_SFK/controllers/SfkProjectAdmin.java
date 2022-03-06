package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.*;
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
                    System.out.println("Estoy dentro del enpoint projectAdmin");
                    String idCompany = ctx.formParam("idCompany");
                    System.out.println("Id company recibido: " +idCompany);
                    String city = ctx.formParam("city");
                    String direction = ctx.formParam("direction");

                    if(idCompany !=null && city!=null && direction !=null){
                        Address address = new Address(city,direction);
                        Company company = ControllerCore.getInstance().findOrganizationById(Long.parseLong(idCompany));
                        if(company !=null){
                            BranchOffice branchOffice = new BranchOffice(address,company);
                            if(BranchOfficeServices.getInstance().findBranchOfficeByAddress(city,direction) !=null){
                                ControllerCore.getInstance().createBranchOffice(branchOffice);
                            }else{
                                System.out.println("Existe una misma sucursal con esta direccion");
                            }
                        }
                    }
                    makeListHeader();
                    model.put("branchOfficeList",BranchOfficeServices.getInstance().findAll());
                    ctx.render("/public/FrontEnd_SFK/views/projectAdminPortal.html",model);
                });
                get("/", ctx ->{
                    makeListHeader();
                    ctx.render("/public/FrontEnd_SFK/views/projectAdminPortal.html",model);
                });
                post("/shelfManagement", ctx ->{
                    String branchOfficeShelf = ctx.formParam("branchOffice");
                    System.out.println("Branch office: "+branchOfficeShelf);
                    BranchOffice branchOffice = null;
                    if(branchOfficeShelf !=null){
                        branchOffice = ControllerCore.getInstance().findBranchOfficeById(Long.parseLong(branchOfficeShelf));
                    }
                    if(branchOffice !=null){
                        Shelf shelf = new Shelf(branchOffice);
                        ControllerCore.getInstance().addShelf(shelf);
                    }
                    makeListHeader();
                    ctx.render("/public/FrontEnd_SFK/views/shelfManagement.html",model);

                });
                get("/shelfManagement", ctx ->{
                    makeListHeader();
                    ctx.render("/public/FrontEnd_SFK/views/shelfManagement.html",model);
                });
                post("/containerManagement", ctx ->{
                    String branchOfficeContainer = ctx.formParam("branchOffice");
                    System.out.println("Branch office container: "+branchOfficeContainer);
                    BranchOffice branchOffice = null;
                    if(branchOfficeContainer !=null){
                        branchOffice = ControllerCore.getInstance().findBranchOfficeById(Long.parseLong(branchOfficeContainer));
                    }
                    if(branchOffice !=null){
                        Container container = new Container(branchOffice);
                        ControllerCore.getInstance().addContainer(container);
                    }
                    System.out.println("Estoy en container list");
                    makeListHeader();
                    model.put("containerList",ContainerServices.getInstance().findAll());
                    ctx.render("/public/FrontEnd_SFK/views/containerManagement.html",model);
                });
                get("/containerManagement", ctx ->{
                    System.out.println("Estoy en container list");
                    makeListHeader();
                    model.put("containerList",ContainerServices.getInstance().findAll());
                    ctx.render("/public/FrontEnd_SFK/views/containerManagement.html",model);
                });
                post("/editContainer", ctx ->{
                    System.out.println("Estoy en edit container");
                    String idCompany = ctx.formParam("idCompanyContainer");
                    String idBranchOffice = ctx.formParam("idBranchOffice");
                    BranchOffice branchOffice = null;
                    if(idBranchOffice !=null){
                        branchOffice = ControllerCore.getInstance().findBranchOfficeById(Long.parseLong(idBranchOffice));
                    }
                    if(branchOffice !=null){
                        Container container1 = new Container();
                        container1.setBranchOffice(branchOffice);

                        ControllerCore.getInstance().updateContainer(container1);
                    }

                    model.put("containerList",ContainerServices.getInstance().findAll());
                    ctx.render("/public/FrontEnd_SFK/views/containerManagement.html",model);
                });
                get("/editContainer",ctx -> {
                    model.put("containerList",ContainerServices.getInstance().findAll());
                    ctx.render("/public/FrontEnd_SFK/views/containerManagement.html",model);
                });
            });

        });
    }
    private void makeListHeader(){
        model.put("allCompany",CompanyServices.getInstance().findAll().size());
        model.put("allBranchOffice",BranchOfficeServices.getInstance().findAll().size());
        model.put("allShelf", ShelfServices.getInstance().findAll().size());
        model.put("allContainer", ContainerServices.getInstance().findAll().size());
        model.put("branchOfficeList",BranchOfficeServices.getInstance().findAll());
    }
}
