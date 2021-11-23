package WEBAPP_SFK;

import WEBAPP_SFK.controllers.WebSocketController;
import WEBAPP_SFK.services.connect.DataBaseServices;
import WEBAPP_SFK.utilities.DefaultDataLoader;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import java.sql.SQLException;

public class Main {
    private static String connectionMode = "";
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

            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/routesControl"));
            config.enableCorsForAllOrigins();

            JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

        });
        new WebSocketController(app).aplicarRutas();
        //new UserController(app).aplicarRutas();

        //  ControllerCore.getInstance().createFakeDataToBD();
        //  DefaultDataLoader.getInstance().createDefaultSuperUser();
       //   DefaultDataLoader.getInstance().createDefaultShelfData();
        //DefaultDataLoader.getInstance().createDefaultContainerData();
        //DefaultDataLoader.getInstance().createDefaultUsers();
        app.get("/", ctx -> {
            ctx.render("/public/dashboardv4.html");
        });


        app.start(7000);
    }
    public static String getModoConexion() {
        return connectionMode;
    }
}
