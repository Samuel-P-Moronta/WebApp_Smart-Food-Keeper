package WEBAPP_SFK;

import WEBAPP_SFK.controllers.ControllerCore;
import WEBAPP_SFK.controllers.WebSocketController;
import WEBAPP_SFK.models.*;
import WEBAPP_SFK.services.connect.DataBaseServices;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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
        System.out.println("Shelf Data List: " + ControllerCore.getInstance().getShelfDataByShelf("SH001"));
      //  ControllerCore.getInstance().createFakeDataToBD();

        app.start(7000);
    }
    public static String getModoConexion() {
        return connectionMode;
    }
}
