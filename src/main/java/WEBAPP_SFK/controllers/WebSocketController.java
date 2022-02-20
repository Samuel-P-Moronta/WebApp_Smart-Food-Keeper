package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.*;
import WEBAPP_SFK.services.ShelfDataServices;
import WEBAPP_SFK.services.ShelfServices;
import WEBAPP_SFK.utilities.Logger;
import com.google.gson.Gson;
import io.javalin.Javalin;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.*;

public class WebSocketController extends BaseController {
    public static List<Session> USERS_CONNECTED_C = new ArrayList<>();
    public static List<Session> USERS_CONNECTED_S = new ArrayList<>();
    public static List<Session> USERS_CONNECTED_N = new ArrayList<>();

    private Map<String, Object> model = new HashMap<>();


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
                System.out.println("Conexion Iniciada - " + ctx.getSessionId());
                USERS_CONNECTED_S.add(ctx.session);
                //sendLastDataFromShelf();


            });
            ws.onMessage(ctx -> {
                System.out.println("RECIBIENDO MENSAJE DEL CLIENT [SHELF]");
                System.out.println("Informacion recibida: " + ctx.message());
                System.out.println("Informacion recibido de : " + ctx.getSessionId());
                Gson gson = new Gson();
                ShelfDataJSON sdj = gson.fromJson(ctx.message(), ShelfDataJSON.class);

                addDataToShelf(Collections.singletonList(sdj));

            });
            ws.onClose(ctx -> {
                System.out.println("La conexión se ha  cerrado - " + ctx.getSessionId());
                USERS_CONNECTED_S.remove(ctx.session);
                //sendLastDataFromShelf();
            });
            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
            ws.onBinaryMessage(ctx -> {
                System.out.println("Mensaje Recibido Binario " + ctx.getSessionId() + " ====== ");
            });

        });

        app.ws("/server/container", ws -> {
            ws.onConnect(ctx -> {
                System.out.println("Conexion Iniciada - " + ctx.getSessionId());
                USERS_CONNECTED_C.add(ctx.session);
            });
            ws.onMessage(ctx -> {
                System.out.println("RECIBIENDO MENSAJE DEL CLIENT [CONTAINER]+");
                Gson gson = new Gson();
                System.out.println("Informacion recibida: " + ctx.message());
                System.out.println("Informacion recibido de : " + ctx.getSessionId());


                ContainerDataJSON containerDataJSON = gson.fromJson(ctx.message(), ContainerDataJSON.class);
                //addDataToContainer(Collections.singletonList(containerDataJSON));


            });
            ws.onClose(ctx -> {
                System.out.println("La conexión se ha  cerrado - " + ctx.getSessionId());
                USERS_CONNECTED_C.remove(ctx.session);
            });
            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
            ws.onBinaryMessage(ctx -> {
                System.out.println("Mensaje Recibido Binario " + ctx.getSessionId() + " ====== ");
            });

        });

    }

    public void addDataToContainer(List<ContainerDataJSON> c) {
        ContainerData auxContainerData = null;
        for (ContainerDataJSON f : c) {
            auxContainerData = new ContainerData(f.getWeight(), ControllerCore.getInstance().findContainerById(1), f.getStatusCode());
            for (Session s : USERS_CONNECTED_C) {
                try {
                    Logger.getInstance().getLog(this.getClass()).info("Sending msg to connect clients [...]");
                    s.getRemote().sendString(new Gson().toJson(auxContainerData));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Logger.getInstance().getLog(this.getClass()).info("Saving into data base from websocket client [...]");
            ControllerCore.getInstance().createContainerData(auxContainerData);
        }
    }

    private void addDataToShelf(List<ShelfDataJSON> shelf) {
        ShelfData auxShelfData = null;
        Shelf auxShelf = null;

        for (ShelfDataJSON f : shelf) {
            Date currentSampleDate = new Date(System.currentTimeMillis());

            auxShelf = ControllerCore.getInstance().getShelfByDeviceName("SH001");

            auxShelfData = new ShelfData(f.getTemperature(),f.getHumidity(), f.getFruitCant(),
                    f.getFruitType(), f.getCantOverripe(), f.getCantRipe(), f.getCantUnripe(), currentSampleDate,
                    auxShelf);
            for (Session session : USERS_CONNECTED_S) {
                try {
                    Logger.getInstance().getLog(this.getClass()).info("Sending msg shelf data to connect clients [...]");
                    session.getRemote().sendString(new Gson().toJson(auxShelfData));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Logger.getInstance().getLog(this.getClass()).info("Saving into data base from websocket client [...]");

          //  ControllerCore.getInstance().addShelfData(auxShelfData);
        }
    }

    public void sendLastDataFromShelf(){
        ShelfData auxShelfData = null;

        int fruitCant = ShelfDataServices.getInstance().getLastRecognitionData(0);
        String fruitType = ShelfDataServices.getInstance().getLastFruitType();
        int cantOverripe = ShelfDataServices.getInstance().getLastRecognitionData(1);
        int cantRipe = ShelfDataServices.getInstance().getLastRecognitionData(2);
        int cantUnripe = ShelfDataServices.getInstance().getLastRecognitionData(3);

        Float temperature = ShelfDataServices.getInstance().getLastEnvironmentalData(0);
        Float humidity = ShelfDataServices.getInstance().getLastEnvironmentalData(1);

        Shelf auxShelf = ControllerCore.getInstance().getShelfByDeviceName("SH001");


        auxShelfData = new ShelfData(temperature,humidity,fruitCant,fruitType,cantOverripe,cantRipe,cantUnripe,null,auxShelf);
        for(Session sesionConectada : USERS_CONNECTED_S){
            try {
                Logger.getInstance().getLog(this.getClass()).info("Sending last data from shelf when websocket disconnect");
                sesionConectada.getRemote().sendString(new Gson().toJson(auxShelfData));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
