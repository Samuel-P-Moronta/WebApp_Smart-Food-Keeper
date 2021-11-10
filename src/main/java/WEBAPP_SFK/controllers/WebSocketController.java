package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class WebSocketController extends BaseController {
    //Creando el repositorio de las sesiones recibidas.
    public static List<Session> usuariosConectados = new ArrayList<>();


    public WebSocketController(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {
        websocket();
    }

    public void websocket(){

        //Por el momento esta no es una solucion eficiente [es para rapido]
        //Luego se pondra el codigo mas eficiente par que solo exista un path para recibir los datos
        // de los sensores de temeperature, humbedad y el peso del safacon
        app.ws("/server/shelf", ws -> {
            ws.onConnect(ctx -> {
                System.out.println("Conexion Iniciada - "+ctx.getSessionId());
                usuariosConectados.add(ctx.session);

            });
            ws.onMessage(ctx -> {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("+           RECIBIENDO MENSAJE DEL CLIENT [SHELF]                      +");
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                ShelfDataJSON shelfDataJSON = convertJacksonToJsonShelf(ctx.message());
                //Adding dato to shelf
                addDataToShelf(Collections.singletonList(shelfDataJSON));
                //System.out.println("Mensaje Recibido de: "+ctx.getSessionId());
                System.out.println("Mensaje: "+ctx.message());

            });
            ws.onClose(ctx -> {
                System.out.println("La conexión se ha  cerrada - "+ctx.getSessionId());
                usuariosConectados.remove(ctx.session);
            });
            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
            ws.onBinaryMessage(ctx -> {
                System.out.println("Mensaje Recibido Binario "+ctx.getSessionId()+" ====== ");
            });

        });
        app.ws("/server/container", ws -> {
            ws.onConnect(ctx -> {
                System.out.println("Conexion Iniciada - "+ctx.getSessionId());
                usuariosConectados.add(ctx.session);

            });
            ws.onMessage(ctx -> {
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("+           RECIBIENDO MENSAJE DEL CLIENT [CONTAINER]                  +");
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

                ContainerDataJSON containerData = convertJacksonToJsonCotainer(ctx.message());
                //Adding data to container
                addDataToCotainer(Collections.singletonList(containerData));

                //System.out.println("Mensaje Recibido de "+ctx.getSessionId()+" ====== ");
                System.out.println("Mensaje: "+ctx.message());

            });
            ws.onClose(ctx -> {
                System.out.println("La conexión se ha  cerrado - "+ctx.getSessionId());
                usuariosConectados.remove(ctx.session);
            });
            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
            ws.onBinaryMessage(ctx -> {
                System.out.println("Mensaje Recibido Binario "+ctx.getSessionId()+" ====== ");
            });

        });

    }
    public int addDataToCotainer(List<ContainerDataJSON> containerDataJSONS){
        ContainerData auxContainerData = null;
        Container auxContainer = null;

        for (ContainerDataJSON f: containerDataJSONS) {
            auxContainer = ControllerCore.getInstance().findContainerById(containerDataJSONS.get(0).getContainer());
            auxContainerData = new ContainerData(f.getWeight(),ControllerCore.getInstance().findContainerById(1),f.getStatusCode());
            if (containerDataJSONS.size() > 0){
                ControllerCore.getInstance().createContainerData(auxContainerData);
            }
        }
        return  containerDataJSONS.size();

    }
    public void allDataShelf(){

    }
    private int addDataToShelf(List<ShelfDataJSON> f1) {
        ShelfData auxShelfData = null;
        Shelf auxShelf = null;

        for (ShelfDataJSON f: f1) {
            Date currentSampleDate = new Date(System.currentTimeMillis());
            auxShelf = ControllerCore.getInstance().getShelfByDeviceName(f1.get(0).getShelf());
            auxShelfData = new ShelfData(f.getTemperature(),f.getHumidity(),f.getFruitCant(),f.getFruitType(),f.getPercentageOverripe(),f.getPercentageRipe(),f.getPercentageUnripe(),currentSampleDate,auxShelf);
            if (f1.size() > 0){
                ControllerCore.getInstance().addShelfData(auxShelfData);
            }
        }
        return  f1.size();

    }
    public static ShelfDataJSON convertJacksonToJsonShelf(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, ShelfDataJSON.class);
    }
    public static ContainerDataJSON convertJacksonToJsonCotainer(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, ContainerDataJSON.class);
    }
}
