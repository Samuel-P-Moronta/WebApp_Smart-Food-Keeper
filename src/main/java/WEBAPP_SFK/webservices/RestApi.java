package WEBAPP_SFK.webservices;

import WEBAPP_SFK.controllers.BaseController;
import WEBAPP_SFK.controllers.ControllerCore;
import WEBAPP_SFK.models.*;
import WEBAPP_SFK.models.enums.RoleApp;
import WEBAPP_SFK.services.*;
import WEBAPP_SFK.utilities.JSONParser;
import com.google.gson.JsonObject;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RestApi extends BaseController {
    private Map<String, Object> errors = new HashMap<>();
    private Map<String, Object> model = new HashMap<>();


    public RestApi(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {

        app.routes(() -> {

            path("/api", () -> {
                after(ctx -> {
                    ctx.header("Content-Type", "application/json");
                });
                get("/findBranchOfficeByCompany/:idCompany", ctx -> {
                    long idCompany = Long.parseLong(ctx.pathParam("idCompany",String.class).get());
                    System.out.println("Company: "+ idCompany);
                    Company company = ControllerCore.getInstance().findOrganizationById(idCompany);
                    ctx.json(company.getBranchOfficeList());
                });
                get("/companyList", ctx -> {
                    ctx.json(new CompanyServices().findAll());
                });
                get("/branchOfficeList", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    Company company = user.getCompany();

                    Set<BranchOffice> branchOfficeList;
                    if(user !=null){
                        if(company !=null){
                            branchOfficeList = user.getCompany().getBranchOfficeList();
                            ctx.json(branchOfficeList);
                        }
                    }
                });
                post("/branchOfficeEmployee", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    String email = user.getEmail();
                    if(user.hasRole(RoleApp.ROLE_EMPLOYEE)){
                        BranchOffice branchOffice = BranchOfficeServices.getInstance().findBranchOfficeByUserEmployee(email);
                        ctx.json(branchOffice);

                    }
                });
                get("/findShelfByBranchOffice/:idBranchOffice", ctx -> {
                    String idBranchOffice = ctx.pathParam("idBranchOffice",String.class).get();
                    if(idBranchOffice !=null){
                        long idBranchOfficeAux = Long.parseLong(idBranchOffice);
                        BranchOffice branchOffice = ControllerCore.getInstance().findBranchOfficeById(idBranchOfficeAux);
                        List<Shelf> shelfList = ShelfServices.getInstance().findShelfByBranchOffice(branchOffice.getId());
                        shelfList.stream().forEach(shelf -> shelf.getDeviceId());
                        ctx.json(shelfList);
                    }
                });
                get("/findContainerByBranchOffice/:idBranchOffice", ctx -> {
                    String idBranchOffice = ctx.pathParam("idBranchOffice",String.class).get();
                    if(idBranchOffice!=null){
                        System.out.println("Branch office: "+ idBranchOffice);
                        long idBranchOfficeAux = Long.parseLong(idBranchOffice);
                        BranchOffice branchOffice = ControllerCore.getInstance().findBranchOfficeById(idBranchOfficeAux);
                        List<Container> containerList = ContainerServices.getInstance().findContainerfByBranchOffice(branchOffice.getId());
                        containerList.stream().forEach(container -> container.getId());
                        ctx.json(containerList);
                    }
                });
                get("/findShelfByDeviceId/:idShelf", ctx -> {
                    String deviceId = ctx.pathParam("idShelf");
                    Shelf shelf = ControllerCore.controllerCore.findShelfByDeviceId(deviceId);
                    ctx.json(shelf);
                });
                get("/shelfList", ctx -> {
                    List<Shelf> shelfList;
                    shelfList = new ShelfServices().findAll();
                    ctx.json(shelfList);
                });
                get("/containerList", ctx -> {
                    List<Container> containerList;
                    containerList = new ContainerServices().findAll();
                    ctx.json(containerList);
                });
            });
        });
    }
}
