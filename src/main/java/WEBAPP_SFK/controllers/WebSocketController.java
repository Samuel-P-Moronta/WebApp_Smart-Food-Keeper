package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.*;
import WEBAPP_SFK.models.enums.NotificationStatus;
import WEBAPP_SFK.services.*;
import WEBAPP_SFK.utilities.EmailSender;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.javalin.Javalin;
import io.javalin.websocket.WsContext;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static WEBAPP_SFK.utilities.CustomEmailSender.htmlMessage;

public class WebSocketController extends BaseController {
    private static Map<WsContext, String> userUsernameMapShelf = new ConcurrentHashMap<>();
    private static Map<WsContext, String> userUsernameMapContainer = new ConcurrentHashMap<>();
    private static Map<WsContext, String> userUsernameForms = new ConcurrentHashMap<>();

    private static Map<String, Float> map = new HashMap<>();
    private static Map<Float, String> newMap = new HashMap<>();
    private static ArrayList<Float> weights = new ArrayList<Float>();
    private Map<String, Object> model = new HashMap<>();



    //private static Map<String, Float> lastEntry = new HashMap<>();


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
                String username = "Shelf" + " " + nextShelfNumber;
                userUsernameMapShelf.put(ctx, username);
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
                    for (User u : userList) {
                        if (u != null) {
                            saveNotifications(sdj, u);
                        } else {
                            System.out.println("No fue encontrado");
                        }
                    }
                } catch (Exception e) {
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
                String username = "Container" + " " + nextContainerfNumber;
                userUsernameMapContainer.put(ctx, username);
            });
            ws.onMessage(ctx -> {
                ctx.sessionAttribute("user");
                System.out.println("RECIBIENDO MENSAJE DEL CLIENT [CONTAINER]+");
                System.out.println("Informacion recibida: " + ctx.message());

                Gson gson = new Gson();
                ContainerDataJSON cdj = gson.fromJson(ctx.message(), ContainerDataJSON.class);
                addDataContainerToDb(cdj);
                addWasteData(cdj);
                broadcastContainerMessage(cdj);
            });
            ws.onClose(ctx -> {
                userUsernameMapContainer.remove(ctx.session);
            });
            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
        });
        app.ws("/form-register", ws -> {

            ws.onConnect(ctx -> {
                String username = "Forms" + " " + nextShelfNumber;
                userUsernameForms.put(ctx, username);
            });
            ws.onMessage(ctx -> {
                ctx.sessionAttribute("user");
                User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                String email = user.getEmail();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    System.out.println("RECIBIENDO MENSAJE DEL CLIENT [FORMS]");
                    System.out.println("Informacion recibida: " + ctx.message());
                    Gson gson = new Gson();
                    //ShelfDataJSON sdj = gson.fromJson(ctx.message(), ShelfDataJSON.class);
                    List<FormAuxJSON> formList = objectMapper.readValue(ctx.message(), new TypeReference<List<FormAuxJSON>>() {
                    });
                    //FormAuxJSON form = gson.fromJson(ctx.message(), FormAuxJSON.class);
                    for (FormAuxJSON formAuxJSON : formList) {
                        long idNotification = Long.parseLong(String.valueOf(formAuxJSON.getIdNotificationSelect()));
                        Notification notification = NotificationServices.getInstance().find(idNotification);
                        if(formAuxJSON.isInspectionNotOverripe() == true){
                            if(notification!=null){
                                notification.setStatus(true);
                                NotificationServices.getInstance().update(notification);
                            }
                        }
                        if(formAuxJSON.getInspectionType() !=null){
                            if (formAuxJSON.getInspectionType().equalsIgnoreCase("EXPRESS") || formAuxJSON.getInspectionType().equalsIgnoreCase("ZAFACON")) {
                                //Notification n = new Notification();
                                if (notification != null) {
                                    notification.setStatus(true);
                                    NotificationServices.getInstance().update(notification);

                                }
                            }
                            if (user != null) {
                                BranchOffice branchOffice = BranchOfficeServices.getInstance().findBranchOfficeByUserEmployee(email);
                                if (branchOffice != null) {
                                    List<Client> clientList = new ClientServices().findAll();
                                    Form f1 = new Form();
                                    f1.setIdNotificationSelect(formAuxJSON.getIdNotificationSelect());
                                    f1.setShelfId(formAuxJSON.getShelfId());
                                    f1.setFruitType(formAuxJSON.getFruitType());
                                    f1.setQuantity(formAuxJSON.getQuantity());
                                    f1.setInspectionType(formAuxJSON.getInspectionType());
                                    f1.setRegisterDate(new Date());
                                    f1.setBranchOffice(branchOffice);
                                    FormServices.getInstance().create(f1);
                                    //Publish express
                                    String customSubject = "Frutas express.";
                                    if(formAuxJSON.getInspectionType().equalsIgnoreCase("EXPRESS")){
                                        for (Client c : clientList) {
                                            String customMessage = "Hola " + " " + c.getFirstName()+" "+ c.getLastName() + "." + " " + "Este correo es para informarte tenemos" + " " + formAuxJSON.getFruitType() + "S" + " " + "con un " + formAuxJSON.getDiscountPercentage() + "%" + " " + "de descuento." + " " + "En el supermercado:" + " " + branchOffice.getCompany().getName() + " " + "y sucursal" + " " + branchOffice.getAddress().getCity() + " " + "(" + branchOffice.getAddress().getDirection() + ")";
                                            new EmailSender().message(c.getEmail(), customSubject, customMessage);
                                        }
                                    }
                                }
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            ws.onClose(ctx -> {
                userUsernameForms.remove(ctx);
            });
            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
        });
    }

    private void addDataShelfToDb(ShelfDataJSON s) {
        Shelf shelfAux = ControllerCore.getInstance().findShelfByDeviceId(s.getDeviceId());
        if (shelfAux != null) {
            ShelfData shelfDataAux = new ShelfData(s.getTemperature(), s.getHumidity(), s.getFruitCant(), s.getFruitType(), s.getCantOverripe(), s.getCantRipe(), s.getCantUnripe(), shelfAux);
            if (shelfDataAux != null) {
                ControllerCore.getInstance().addShelfData(shelfDataAux);
            }
        }

    }

    private void addDataContainerToDb(ContainerDataJSON c) {
        Container containerAux = ControllerCore.getInstance().findContainerById(c.getContainerId());
        if (containerAux != null) {
            ContainerData containerDataAux = new ContainerData(c.getWeight(), containerAux.getContainerId());
            if (containerDataAux != null) {
                ControllerCore.getInstance().addContainerData(containerDataAux);
            }
        }
    }

    private void addWasteData(ContainerDataJSON c) {
        Float containerData = ContainerDataServices.getInstance().getLastData();
        Container container = ContainerServices.getInstance().find(c.getContainerId());
        System.out.println("Valor pasado: " + containerData);
        System.out.println("Valor actual: " + c.getWeight());

        WasteData wasteData = new WasteData();
        float saveValue;
        float containerDataFlag;
        if (containerData == null) {
            containerData = 0.0F;
        } else {

            containerDataFlag = containerData;
            if ((c.getWeight() - containerDataFlag) < 0) {
                saveValue = (c.getWeight() - containerDataFlag) * -1;
                System.out.println("Valor a guardar: " + saveValue);
                if (container != null) {
                    wasteData.setWasteData(saveValue);
                    wasteData.setSendDate(new Date());
                    wasteData.setContainer(container);
                    WasteDataServices.getInstance().create(wasteData);
                }
            }
        }
    }

    public float wasteData(float expectedValue, ArrayList<Float> data) {
        float secondLast = 0.0F;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == expectedValue) {
                if (i == 0) {
                    continue;
                } else {
                    secondLast = data.get(i - 1);
                }
                break;
            }
        }
        return secondLast;
    }

    private static void broadcastShelfMessage(ShelfDataJSON s) {
        ShelfDataJSON shelfDataJSON = new ShelfDataJSON(s.getTemperature(), s.getHumidity(), s.getFruitCant(), s.getFruitType(), s.getCantOverripe(), s.getCantRipe(), s.getCantUnripe(), s.getDeviceId());
        userUsernameMapShelf.keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
            session.send(new Gson().toJson(shelfDataJSON));
        });
    }

    private static void broadcastContainerMessage(ContainerDataJSON c) {
        ContainerDataJSON containerDataJSON = new ContainerDataJSON(c.getWeight(), c.getContainerId());
        userUsernameMapContainer.keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
            session.send(new Gson().toJson(containerDataJSON));
        });
    }

    public void saveNotifications(ShelfDataJSON sdj, User user) {
        /*
        boolean notificationMadurez = new ControllerCore().findNotificationByTypeAndUser(1, user);
        boolean notificationSuministro = new ControllerCore().findNotificationByTypeAndUser(2, user);
        boolean notificationTemperature = new ControllerCore().findNotificationByTypeAndUser(3, user);
        boolean notificationHumidity = new ControllerCore().findNotificationByTypeAndUser(4, user);

         */
        List<ShelfData> shelfData = new ShelfDataServices().findAllShelfDataByShelfId(sdj.getDeviceId());

        boolean notificationMadurez = new ControllerCore().findNotificationByActive(false,1,user);
        boolean notificationSuministro = new ControllerCore().findNotificationByActive(false,2,user);
        boolean notificationTemperature = new ControllerCore().findNotificationByActive(false,3,user);
        boolean notificationHumidity = new ControllerCore().findNotificationByActive(false,4, user);

        if (user != null) {
            if (sdj.getCantOverripe() > 0) {
                if (notificationMadurez == true) {
                    System.out.println("This notification [MADUREZ] esta en proceso de revision");
                } else {
                    for (ShelfData sData : shelfData) {
                        Notification notificationType1 = new Notification("Madurez", NotificationStatus.MADUREZ.getMessage(), new Date(), user, user.getBranchOffice(), user.getCompany(), 1, sData, false);
                        ControllerCore.getInstance().createNotification(notificationType1);
                    }
                    try {
                        new EmailSender().message(user.getEmail(), NotificationStatus.MADUREZ.getMessage(), htmlMessage());
                    } catch (Exception e) {
                        System.out.println("NO SE PUDO ENVIAR EL MENSAJE...");
                        e.printStackTrace();
                    }
                }
            }
            if (sdj.getFruitCant() <= 1) {
                if (notificationSuministro == true) {
                    System.out.println("This notification [SUMINISTRO] esta en proceso de revision");
                } else {
                    Notification notificationType2 = new Notification("Suministro", NotificationStatus.SUMINISTRO.getMessage(), new Date(), user, user.getBranchOffice(), user.getCompany(), 2, false);
                    ControllerCore.getInstance().createNotification(notificationType2);
                    try {
                        new EmailSender().message(user.getEmail(), NotificationStatus.SUMINISTRO.getMessage(), htmlMessage());
                    } catch (Exception e) {
                        System.out.println("NO SE PUDO ENVIAR EL MENSAJE...");
                        e.printStackTrace();
                    }
                }
            }
            if (sdj.getTemperature() > 30) {
                if (notificationTemperature == true) {
                    System.out.println("This notification [TEMPERATURE] esta en proceso de revision");
                } else {
                    Notification notificationType3 = new Notification("Temperatura", NotificationStatus.TEMPERATURA.getMessage(), new Date(), user, user.getBranchOffice(), user.getCompany(), 3, false);
                    ControllerCore.getInstance().createNotification(notificationType3);
                    try {
                        new EmailSender().message(user.getEmail(), NotificationStatus.TEMPERATURA.getMessage(), htmlMessage());
                    } catch (Exception e) {
                        System.out.println("NO SE PUDO ENVIAR EL MENSAJE...");
                        e.printStackTrace();
                    }
                }
            }
            if (sdj.getHumidity() > 90) {
                if (notificationHumidity == true) {
                    System.out.println("This notification [HUMIDITY] esta en proceso de revision");
                } else {
                    Notification notificationType4 = new Notification("Humedad", NotificationStatus.HUMEDAD.getMessage(), new Date(), user, user.getBranchOffice(), user.getCompany(), 4, false);
                    ControllerCore.getInstance().createNotification(notificationType4);
                    try {
                        new EmailSender().message(user.getEmail(), NotificationStatus.HUMEDAD.getMessage(), htmlMessage());
                    } catch (Exception e) {
                        System.out.println("NO SE PUDO ENVIAR EL MENSAJE...");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
