package WEBAPP_SFK;

import WEBAPP_SFK.controllers.ControllerCore;
import WEBAPP_SFK.controllers.WebSocketController;
import WEBAPP_SFK.models.*;
import WEBAPP_SFK.services.connect.DataBaseServices;
import WEBAPP_SFK.utilities.DefaultDataLoader;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
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

            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/routesControl"));
            config.enableCorsForAllOrigins();

            JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

        });
        DefaultDataLoader.getInstance().createDefaultSuperUser();
        new WebSocketController(app).aplicarRutas();
        //new UserController(app).aplicarRutas();
        //System.out.println(s.getLastDataFromShelf());
        //1
        /* TESTING CRUD ORGANIZATION */
        /*Creating*/
        //DefaultDataLoader.getInstance().createDefaultOrganization();
        /* TESTING CRUD BRANCH OFFICE */
        /* Creating */
       // DefaultDataLoader.getInstance().createDefaultBranchOffice();
        /*Deleting*/
       // ControllerCore.getInstance().deleteBranchOffice(1);
        /*Updating*/
        /*
        Address address = new Address("Calle bonita","La vega" );
        BranchOffice branchOffice = ControllerCore.getInstance().findBranchOfficeById(129);
        if(branchOffice != null){
            branchOffice.setAddress(address);
            ControllerCore.getInstance().updateBranchOffice(branchOffice);
        }
         */
        /* TESTING CRUD PERSON */

        /*Updating*/
  //      Address address = new Address("Puerto Plata", "Calle capotillo 16");
//        Person person = ControllerCore.getInstance().findPersonById(1);
  //      if(person !=null){
   //         person.setFirstName("Oye eso");
   //         person.setAddress(address);
   //         ControllerCore.getInstance().updatePerson(person);
    //    }
        /* TESTING CRUD USER */
        /*Updating*/
        /* TESTING CRUD SHELF */
        /*Updating*/
        /*Deleting*/
        /* TESTING CRUD CONTAINER */
        /*Updating*/
        /*Deleting*/
        /* TESTING CRUD NOTIFICATION */

        /*
        User user = ControllerCore.getInstance().findUserByEmail("employee@gmail.com");

       // boolean notification1 = new ControllerCore().findNotificationByTypeAndUser(1,user);
        if(user !=null){
            if(notification1 == true){
                System.out.println("Notificaciones repetidas");
            }else{
                System.out.println("Crear la notificacion");
             //   Notification notificationCreate = new Notification("Madurez",new Date(), "Existen frutas en estado muy maduras", user,1);
               // ControllerCore.getInstance().createNotification(notificationCreate);
            }
        }

         */



        /*Just Deleting*/

        /* TESTING HELPER METHODS */
        /*
        Organization organization1 = new ControllerCore().findOrganizationByBranchOffice(100);

        if(organization1 !=null){
            System.out.println("Organization found");
        }
        else{
            System.out.println("Organization not found");

        }

         */
       // DefaultDataLoader.getInstance().createDefaultShelfData();
      //  DefaultDataLoader.getInstance().createDefaultContainerData();

        /*
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

         */



        app.start(7000);
    }
    public static String getModoConexion() {
        return connectionMode;
    }
}
