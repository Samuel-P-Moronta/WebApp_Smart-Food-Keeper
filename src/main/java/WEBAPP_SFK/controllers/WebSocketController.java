package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.models.ShelfDataJSON;
import com.fasterxml.jackson.core.type.TypeReference;
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
        webSockert();

    }

    public void webSockert(){
        /**
         * Definición del WebSockert en Javalin en contexto.
         */
        app.ws("/conectarServidor", ws -> {
            ws.onConnect(ctx -> {
                System.out.println("Conexion Iniciada - "+ctx.getSessionId());
                usuariosConectados.add(ctx.session);

            });
            ws.onMessage(ctx -> {
                ObjectMapper objectMapper = new ObjectMapper();
                System.out.println("Recibiendo Mesaje por WS...");
                ShelfDataJSON sh1 = convertJacksonToJson(ctx.message());
                addDataToShelf(Collections.singletonList(sh1));
                System.out.println("Mensaje Recibido de "+ctx.getSessionId()+" ====== ");
                System.out.println("Mensaje: "+ctx.message());
                System.out.println("================================");

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

    }

    private int addDataToShelf(List<ShelfDataJSON> f1) {
        ShelfData auxShelfData = null;
        Shelf auxShelf = null;

        for (ShelfDataJSON f: f1) {
            int i = 0;
            Date currentSampleDate = new Date(System.currentTimeMillis());
            auxShelf = ControllerCore.getInstance().getShelfByDeviceName(f1.get(0).getShelf());
            auxShelfData = new ShelfData(f.getTemperature(),f.getHumidity(),f.getFruitCant(),f.getFruitType(),f.getPercentageOverripe(),f.getPercentageRipe(),f.getPercentageUnripe(),currentSampleDate,auxShelf);
            if (f1.size() > 0){
                ControllerCore.getInstance().addShelfData(auxShelfData);
            }
            else{
                //Create fake shelf if not exist
                String deviceDame = "SH"+i;
                Shelf sf = new Shelf(deviceDame,"2021-30-10 02:51:02");
                ControllerCore.getInstance().addShelf(sf);
            }
            i++;
        }
        return  f1.size();

    }
    public static ShelfDataJSON convertJacksonToJson(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonString, ShelfDataJSON.class);
    }
}
