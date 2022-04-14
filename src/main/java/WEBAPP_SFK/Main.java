package WEBAPP_SFK;

import WEBAPP_SFK.controllers.*;
import WEBAPP_SFK.models.*;
import WEBAPP_SFK.services.ContainerDataServices;
import WEBAPP_SFK.services.NotificationServices;
import WEBAPP_SFK.services.WasteDataServices;
import WEBAPP_SFK.services.connect.DataBaseServices;
import WEBAPP_SFK.utilities.DefaultDataLoader;
import WEBAPP_SFK.webservices.RestApi;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class Main {
    private static String connectionMode = "";
    private static Map<String, Object> model = new HashMap<>();




    public static void main(String[] args) throws SQLException, InterruptedException, ParseException {
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
        long idNotification = Long.parseLong(String.valueOf(1));
        long idNotification2 = Long.parseLong(String.valueOf(2));
        Notification notification = NotificationServices.getInstance().find(idNotification);
        Notification notification2 = NotificationServices.getInstance().find(idNotification2);
        System.out.println("Estante: "+notification.getShelfData().getShelf().getDeviceId());
        System.out.println("Tipo de fruta 1: "+notification.getShelfData().getFruitType());
        System.out.println("Cantidad de frutas: "+notification.getShelfData().getFruitCant());
        System.out.println("Cantidad overripe: "+notification.getShelfData().getCantOverripe());
        System.out.println("Tipo de fruta 2: "+notification2.getShelfData().getFruitType());
        System.out.println("Cantidad overripe: "+notification2.getShelfData().getCantOverripe());
        System.out.println("Cantidad de frutas: "+notification2.getShelfData().getFruitCant());


        //DefaultDataLoader.getInstance().createDefaultContainer();
        /*
        ArrayList<Float> list = new ArrayList<Float>();
        list.add(1.0F);
        list.add(3.2F);
        list.add(5.2F);
        list.add(0.0F);
        list.add(2.0F);
        list.add(4.2F);
        float restar = 0.0F;
        float expectedValue = 0.0F;
        float secontLast = -1;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i) == expectedValue){
                if(i == 0){
                    System.out.println("Initial position");
                }else{
                    secontLast = list.get(i - 1);
                    System.out.println("ULTIMO VAL0R ANTES DE LLEGAR A 0: "+secontLast);
                }
                break;
            }
        }

         */
        //System.out.println("PRUEBA waste: "+WasteDataServices.getInstance().wasteFruitsWeight(new Date(),2));
        app.start(7000);
    }
    public static String getModoConexion() {
        return connectionMode;
    }
}
