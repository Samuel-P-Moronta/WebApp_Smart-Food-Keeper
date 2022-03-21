package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.*;
import WEBAPP_SFK.models.enums.NotificationStatus;
import WEBAPP_SFK.services.ContainerServices;
import WEBAPP_SFK.services.ShelfDataServices;
import WEBAPP_SFK.services.ShelfServices;
import WEBAPP_SFK.services.UserServices;
import WEBAPP_SFK.utilities.CustomEmailSender;
import WEBAPP_SFK.utilities.Logger;
import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.websocket.WsContext;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import javax.naming.ldap.Control;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static WEBAPP_SFK.utilities.CustomEmailSender.htmlMessage;

public class WebSocketController extends BaseController {
    public static List<Session> USERS_CONNECTED_C = new ArrayList<>();
    public static List<Session> USERS_CONNECTED_S = new ArrayList<>();
    private static Map<WsContext, String> userUsernameMapShelf = new ConcurrentHashMap<>();
    private static Map<WsContext, String> userUsernameMapContainer = new ConcurrentHashMap<>();

    private static int nextShelfNumber = 1;
    private static int nextContainerfNumber = 1;


    public WebSocketController(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        websocket();
    }

    public void websocket() {

        app.ws("/server/shelf", ws -> {

            ws.onConnect(ctx -> {
                String username = "Shelf"+" "+nextShelfNumber;
                userUsernameMapShelf.put(ctx,username);
            });
            ws.onMessage(ctx -> {
                try {
                    System.out.println("RECIBIENDO MENSAJE DEL CLIENT [SHELF]");
                    System.out.println("Informacion recibida: " + ctx.message());
                    Gson gson = new Gson();
                    ShelfDataJSON sdj = gson.fromJson(ctx.message(), ShelfDataJSON.class);
                    addDataShelfToDb(sdj);
                    broadcastShelfMessage(sdj);
                    String deviceId = sdj.getDeviceId();
                    List<User> userList;
                    userList = new UserServices().findUserByShelf(deviceId);
                    for(User u: userList){
                        if(u !=null){
                            saveNotifications(sdj,u);
                        }else{
                            System.out.println("No fue encontrado");
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            ws.onClose(ctx -> {
                userUsernameMapShelf.remove(ctx);
            });
            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
        });

        app.ws("/server/container", ws -> {
            ws.onConnect(ctx -> {
                String username = "Container"+" "+nextShelfNumber;
                userUsernameMapContainer.put(ctx,username);
            });
            ws.onMessage(ctx -> {
                ctx.sessionAttribute("user");
                System.out.println("RECIBIENDO MENSAJE DEL CLIENT [CONTAINER]+");
                System.out.println("Informacion recibida: " + ctx.message());

                Gson gson = new Gson();
                ContainerDataJSON cdj = gson.fromJson(ctx.message(), ContainerDataJSON.class);
                addDataContainerToDb(cdj);
                broadcastContainerMessage(cdj);
            });
            ws.onClose(ctx -> {
                userUsernameMapContainer.remove(ctx.session);
            });
            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
        });
    }
    private void addDataShelfToDb(ShelfDataJSON s){
        Shelf shelfAux = ControllerCore.getInstance().findShelfByDeviceId(s.getDeviceId());
        if(shelfAux !=null){
            ShelfData shelfDataAux = new ShelfData(s.getTemperature(),s.getHumidity(), s.getFruitCant(), s.getFruitType(), s.getCantOverripe(), s.getCantRipe(), s.getCantUnripe(),shelfAux);
            if(shelfDataAux !=null){
                ControllerCore.getInstance().addShelfData(shelfDataAux);
            }
        }
    }
    private void addDataContainerToDb(ContainerDataJSON c){
        Container containerAux = ControllerCore.getInstance().findContainerById(c.getContainerId());
        if(containerAux !=null){
            ContainerData containerDataAux = new ContainerData(c.getWeight(),containerAux.getContainerId());
            if(containerDataAux !=null){
                ControllerCore.getInstance().addContainerData(containerDataAux);
            }
        }
    }
    private static void broadcastShelfMessage(ShelfDataJSON s) {
        ShelfDataJSON shelfDataJSON = new ShelfDataJSON(s.getTemperature(),s.getHumidity(), s.getFruitCant(), s.getFruitType(), s.getCantOverripe(), s.getCantRipe(), s.getCantUnripe(),s.getDeviceId());
        userUsernameMapShelf.keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
            session.send(new Gson().toJson(shelfDataJSON));
        });
    }
    private static void broadcastContainerMessage(ContainerDataJSON c) {
        ContainerDataJSON containerDataJSON = new ContainerDataJSON(c.getWeight(),c.getContainerId());
        userUsernameMapContainer.keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
            session.send(new Gson().toJson(containerDataJSON));
        });
    }
    public void saveNotifications(ShelfDataJSON sdj, User user){
        boolean notificationMadurez = new ControllerCore().findNotificationByTypeAndUser(1,user);
        boolean notificationSuministro = new ControllerCore().findNotificationByTypeAndUser(2,user);
        boolean notificationTemperature = new ControllerCore().findNotificationByTypeAndUser(3,user);
        boolean notificationHumidity = new ControllerCore().findNotificationByTypeAndUser(4,user);
        boolean notificationHealthy = new ControllerCore().findNotificationByTypeAndUser(5,user);

        if(user!=null){
            if(sdj.getCantOverripe()> 0){
                if(notificationMadurez == true){
                    System.out.println("This notification [MADUREZ] already exist in database");
                }else{
                    Notification notificationType1 = new Notification("Madurez", NotificationStatus.MADUREZ.getMessage(), new Date(),user,user.getBranchOffice(), user.getCompany(), 1);
                    ControllerCore.getInstance().createNotification(notificationType1);
                    try{
                        new CustomEmailSender().message(user.getEmail(), NotificationStatus.MADUREZ.getMessage(),htmlMessage());
                    } catch (Exception  e) {
                        System.out.println("NO SE PUDO ENVIAR EL MENSAJE...");
                        e.printStackTrace();
                    }
                }
            }else{
                if(sdj.getCantRipe() > 0 || sdj.getCantUnripe() > 0){
                    if(notificationHealthy == true){
                        System.out.println("This notification [HEALTHY] already exist in database");
                    }else{
                        Notification notificationType5 = new Notification("Saludable",NotificationStatus.SALUDABLE.getMessage(),new Date(), user, user.getBranchOffice(), user.getCompany(), 5);
                        ControllerCore.getInstance().createNotification(notificationType5);
                    }
                }
            }
            if(sdj.getFruitCant() <= 1){
                if(notificationSuministro == true){
                    System.out.println("This notification [SUMINISTRO] already exist in database");
                }else{
                    Notification notificationType2 = new Notification("Suministro", NotificationStatus.SUMINISTRO.getMessage(), new Date(), user,user.getBranchOffice(), user.getCompany(), 2);
                    ControllerCore.getInstance().createNotification(notificationType2);
                }
            }
            if(sdj.getTemperature() > 30){
                if(notificationTemperature == true){
                    System.out.println("This notification [TEMPERATURE] already exist in database");
                }else{
                    Notification notificationType3 = new Notification("Temperatura",NotificationStatus.TEMPERATURA.getMessage(), new Date(), user,user.getBranchOffice(),user.getCompany(),3);
                    ControllerCore.getInstance().createNotification(notificationType3);
                    try{
                        new CustomEmailSender().message(user.getEmail(),NotificationStatus.TEMPERATURA.getMessage(),htmlMessage());
                    } catch (Exception  e) {
                        System.out.println("NO SE PUDO ENVIAR EL MENSAJE...");
                        e.printStackTrace();
                    }
                }
            }
            if(sdj.getHumidity() < 75){
                if(notificationHumidity == true){
                    System.out.println("This notification [HUMIDITY] already exist in database");
                }else{
                    Notification notificationType4 = new Notification("Humedad",NotificationStatus.HUMEDAD.getMessage(), new Date(), user,user.getBranchOffice(), user.getCompany(), 4);
                    ControllerCore.getInstance().createNotification(notificationType4);
                    try{
                        new CustomEmailSender().message(user.getEmail(),NotificationStatus.HUMEDAD.getMessage(),htmlMessage());
                    } catch (Exception  e) {
                        System.out.println("NO SE PUDO ENVIAR EL MENSAJE...");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
