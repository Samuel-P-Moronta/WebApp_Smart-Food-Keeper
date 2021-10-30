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
import java.util.ArrayList;
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
        /*
        Person p = new Person("0000000000000","prueba@gmail.com","samuel","pena","moronta");
        Person p2 = new Person("0002000000001","prueba@gmail.com","samuel","pena","moronta");
        //ControllerCore.getInstance().addPerson(p2);
       // ControllerCore.getInstance().addPerson(p2);

        //    public User(String email, String username, String password, RoleApp userRole, Person person) {
        User u1 = new User("prueba@gmail.com","samuel","1234", RoleApp.ROLE_ADMIN);
        ControllerCore.getInstance().addUser(u1);
        //ControllerCore.getInstance().addPerson(p);

         */
        //    public Shelf(String device_name, String registerDate, ShelfData shelfdata) {
        //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //    public ShelfData(Double temperature, Double humidity,
        //                     int fruitCant, String fruitType,
        //                     int percentageOverripe, int percentageRipe,
        //                     int percentage_unripe, String currentSampleDate) {
        Shelf sf = new Shelf("SH002","2021-30-10 02:51:02");
        Shelf sf1 = new Shelf("SH003","2021-30-10 02:51:02");
        Shelf sf2 = new Shelf("SH004","2021-30-10 02:51:02");
        Shelf sf3 = new Shelf("SH005","2021-30-10 02:51:02");

        //ShelfData sd = new ShelfData(35.50,60.20,4,"PINEAPPLE",20,30,50,"2021-30-10 02:51:02",sf);
       // ControllerCore.getInstance().addShelf(sf1);

      //  makeFakeMeasures();
       // ControllerCore.getInstance().addShelf(sf);
        //ControllerCore.getInstance().addShelfData(sd);

       // ControllerCore.getInstance().addShelf(sf);
      //  ShelfData sd = new ShelfData(27.67,60.20,4,"PINEAPPLE",20,30,50,"2021-30-10 02:51:02",sf);
     //   ControllerCore.getInstance().addShelfData(sd);
        new WebSocketController(app).aplicarRutas();



        app.start(7000);


    }
    public static String getModoConexion() {
        return connectionMode;
    }


    public static void makeFakeMeasures() throws InterruptedException {


        while (true){
            Double temperature = (Double) Math.floor(Math.random()*(40-20+1)+20);
            Double humidity = (Double) Math.floor(Math.random()*(40-20+1)+20);
            int cantFrutas = 4;
            int percentageOverripe = 25;
            int percentageRipe = 50;
            int percentageUnripe = 25;
            Date currentSampleDate = new Date(System.currentTimeMillis());

            Thread.sleep(10000);
            Shelf sf = new Shelf("SH002","2021-30-10 02:51:02");
            ShelfData sd = new ShelfData(temperature,humidity,cantFrutas,"PINEAPPLE",percentageOverripe,percentageRipe,percentageUnripe,currentSampleDate,sf);
            ControllerCore.getInstance().addShelfData(sd);
        }
    }
}
