package WEBAPP_SFK.webservices;

import WEBAPP_SFK.controllers.BaseController;
import WEBAPP_SFK.models.ErrorResponse;
import WEBAPP_SFK.services.BranchOfficeServices;
import com.google.gson.JsonObject;
import io.javalin.Javalin;
import io.javalin.http.Context;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RestApi extends BaseController {
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
                get("/findBranchOfficeByCompany/:id", ctx -> {
                    long idCompany = Long.parseLong(ctx.pathParam("id",String.class).get());
                    System.out.println("Company: "+ idCompany);
                    ctx.json(new BranchOfficeServices().findBranchOfficeByCompany(idCompany));
                });
            });
        });
    }
}
