package WEBAPP_SFK;

import WEBAPP_SFK.controllers.WebSocketController;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.models.ShelfDataJSON;
import WEBAPP_SFK.services.ShelfDataServices;
import WEBAPP_SFK.services.connect.DataBaseServices;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/routesControl"));
            config.enableCorsForAllOrigins();

            JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

        });
        new WebSocketController(app).aplicarRutas();
        //new UserController(app).aplicarRutas();
        //System.out.println(s.getLastDataFromShelf());

        //  ControllerCore.getInstance().createFakeDataToBD();
        //  DefaultDataLoader.getInstance().createDefaultSuperUser();
        //  DefaultDataLoader.getInstance().createDefaultShelfData();
        //DefaultDataLoader.getInstance().createDefaultContainerData();
        //DefaultDataLoader.getInstance().createDefaultUsers();

        app.get("/", ctx -> {
            Map<String, Object> model = new HashMap<>();

            int fruitCant = ShelfDataServices.getInstance().getLastRecognitionData(0);
            String fruitType = ShelfDataServices.getInstance().getLastFruitType();

            int cantOverripe = ShelfDataServices.getInstance().getLastRecognitionData(1);
            int cantRipe = ShelfDataServices.getInstance().getLastRecognitionData(2);
            int cantUnripe = ShelfDataServices.getInstance().getLastRecognitionData(3);

            Float temperature = ShelfDataServices.getInstance().getLastEnvironmentalData(0);
            Float humidity = ShelfDataServices.getInstance().getLastEnvironmentalData(1);

            model.put("fruitCant",fruitCant);
            model.put("fruitType",fruitType);
            model.put("cantOverripe",cantOverripe);
            model.put("cantRipe",cantRipe);
            model.put("cantUnripe",cantUnripe);
            model.put("temperature",temperature);
            model.put("temperature",humidity);


            ctx.render("/public/dashboardv4.html",model);

        });



        app.start(7000);
    }
    public static String getModoConexion() {
        return connectionMode;
    }
}
