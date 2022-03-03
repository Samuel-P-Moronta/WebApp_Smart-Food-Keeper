package WEBAPP_SFK.webservices;

import WEBAPP_SFK.controllers.BaseController;
import WEBAPP_SFK.controllers.ControllerCore;
import WEBAPP_SFK.models.BranchOffice;
import WEBAPP_SFK.models.Company;
import WEBAPP_SFK.models.ErrorResponse;
import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.services.BranchOfficeServices;
import WEBAPP_SFK.services.CompanyServices;
import WEBAPP_SFK.services.ShelfServices;
import WEBAPP_SFK.utilities.JSONParser;
import com.google.gson.JsonObject;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RestApi extends BaseController {
    private Map<String, Object> errors = new HashMap<>();

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
                    ctx.json(new BranchOfficeServices().findAll());
                });
                get("/findShelfByBranchOffice/:idBranchOffice-S", ctx -> {
                    long idBranchOffice = Long.parseLong(ctx.pathParam("idBranchOffice-S",String.class).get());
                    System.out.println("ID BRANCH OFFICE: "+idBranchOffice);
                    BranchOffice branchOffice = ControllerCore.getInstance().findBranchOfficeById(idBranchOffice);
                    long idBranchOfficeAux = branchOffice.getId();

                    List<Shelf> shelfList = ShelfServices.getInstance().findShelfByBranchOffice(idBranchOfficeAux);
                    shelfList.stream().forEach(shelf -> shelf.getDeviceId());
                    ctx.json(shelfList);
                });
                //findAllShelf
                get("/shelfList", ctx -> {
                    List<Shelf> shelfList;
                    shelfList = new ShelfServices().findAll();
                    ctx.json(shelfList);
                });
            });
        });
    }
}
