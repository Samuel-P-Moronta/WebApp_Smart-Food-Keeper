package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.*;
import WEBAPP_SFK.utilities.Logger;
import com.google.gson.Gson;
import io.javalin.Javalin;
import org.eclipse.jetty.websocket.api.Session;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class WebSocketController extends BaseController {
    //Creando el repositorio de las sesiones recibidas.
    public static List<Session> USERS_CONNECTED_C = new ArrayList<>();
    public static List<Session> USERS_CONNECTED_S = new ArrayList<>();
    private List<ShelfData> quantities = new ArrayList<>();


    public WebSocketController(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        websocket();
    }

    public void websocket() {


        //Por el momento esta no es una solucion eficiente [es para rapido]
        //Luego se pondra el codigo mas eficiente par que solo exista un path para recibir los datos
        // de los sensores de temeperature, humbedad y el peso del safacon
        app.ws("/server/shelf", ws -> {

            ws.onConnect(ctx -> {
                System.out.println("Conexion Iniciada - " + ctx.getSessionId());
                USERS_CONNECTED_S.add(ctx.session);

            });
            ws.onMessage(ctx -> {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("+           RECIBIENDO MENSAJE DEL CLIENT [SHELF]                      +");
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("Mensaje: " + ctx.message());
                Gson gson = new Gson();
                ShelfDataJSON sdj = gson.fromJson(ctx.message(), ShelfDataJSON.class);
                addDataToShelf(Collections.singletonList(sdj));
            });
            ws.onClose(ctx -> {
                System.out.println("La conexión se ha  cerrada - " + ctx.getSessionId());
                USERS_CONNECTED_S.remove(ctx.session);
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
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("+           RECIBIENDO MENSAJE DEL CLIENT [CONTAINER]                  +");
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                Gson gson = new Gson();
                System.out.println("Mensaje: " + ctx.message());
                System.out.println("Mensaje recibido de : " + ctx.getSessionId());


                ContainerDataJSON containerDataJSON = gson.fromJson(ctx.message(), ContainerDataJSON.class);
                addDataToContainer(Collections.singletonList(containerDataJSON));


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
            //ControllerCore.getInstance().createContainerData(auxContainerData);
        }

    }

    public void allDataShelf() {

    }

    private int addDataToShelf(List<ShelfDataJSON> s) {
        ShelfData auxShelfData = null;
        Shelf auxShelf = null;

        for (ShelfDataJSON f : s) {
            Date currentSampleDate = new Date(System.currentTimeMillis());
            auxShelf = ControllerCore.getInstance().getShelfByDeviceName("SH001");
            auxShelfData = new ShelfData(
                    f.getTemperature(),
                    f.getHumidity(),
                    f.getFruitCant(),
                    f.getFruitType(),
                    f.getPercentageOverripe(),
                    f.getPercentageRipe(),
                    f.getPercentageUnripe(),
                    currentSampleDate,
                    auxShelf
            );
            if (s.size() > 0) {
                //ControllerCore.getInstance().addShelfData(auxShelfData);
                for (Session session : USERS_CONNECTED_S) {
                    try {
                        Logger.getInstance().getLog(this.getClass()).info("Sending msg sheld data to connect clients [...]");
                        session.getRemote().sendString(new Gson().toJson(auxShelfData));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return s.size();

    }
}
