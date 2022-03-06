package WEBAPP_SFK;

import WEBAPP_SFK.controllers.*;
import WEBAPP_SFK.models.*;
import WEBAPP_SFK.services.connect.DataBaseServices;
import WEBAPP_SFK.utilities.DefaultDataLoader;
import WEBAPP_SFK.webservices.RestApi;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static String connectionMode = "";
    private static Map<String, Object> model = new HashMap<>();




    public static void main(String[] args) throws SQLException, InterruptedException {
        String msg = "Smart Food Keeper - Final Project";

        System.out.println(msg);
        if(args.length >=1){
            connectionMode = args[0];
            System.out.println("Operation Mode: "+connectionMode);
        }
        if(connectionMode.isEmpty()){
            //Database init
            DataBaseServices.getInstance().initDB();
        }

        Javalin app = Javalin.create(config -> {

            config.addStaticFiles("public/FrontEnd_SFK");
            config.registerPlugin(new RouteOverviewPlugin("/public/FrontEnd_SFK"));
            config.enableCorsForAllOrigins();

            JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

        });
        //DefaultDataLoader.getInstance().createDefaultSuperUser();
        new SFKException(app).aplicarRutas();
        new WebSocketController(app).aplicarRutas();
        new MainController(app).aplicarRutas();
        new SfkProjectAdmin(app).aplicarRutas();
        new RestApi(app).aplicarRutas();


        //DefaultDataLoader.getInstance().createDefaultCompany();
        //DefaultDataLoader.getInstance().createDefaultBranchOffice();
        //DefaultDataLoader.getInstance().createDefaultShelf();
        DefaultDataLoader.getInstance().createDefaultData();
       //DefaultDataLoader.getInstance().createDefaultContainer();


        app.start(7000);
    }
    public static String getModoConexion() {
        return connectionMode;
    }
}
