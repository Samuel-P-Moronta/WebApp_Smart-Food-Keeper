package WEBAPP_SFK.webservices;

import WEBAPP_SFK.controllers.BaseController;
import WEBAPP_SFK.controllers.ControllerCore;
import WEBAPP_SFK.models.*;
import WEBAPP_SFK.models.enums.RoleApp;
import WEBAPP_SFK.services.*;
import io.javalin.Javalin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RestApi extends BaseController {
    private Map<String, Object> errors = new HashMap<>();
    private Map<String, Object> model = new HashMap<>();


    public RestApi(Javalin app) {
        super(app);
    }

    @Override
    public void aplicarRutas() {

        app.routes(() -> {

            path("/api", () -> {
                after(ctx -> {
                    ctx.header("Content-Type", "application/json");
                });
                get("/findBranchOfficeByCompany/:idCompany", ctx -> {
                    long idCompany = Long.parseLong(ctx.pathParam("idCompany", String.class).get());
                    System.out.println("Company ID: " + idCompany);
                    Company company = ControllerCore.getInstance().findCompanyById(idCompany);
                    ctx.json(company.getBranchOfficeList());
                });
                get("/companyList", ctx -> {
                    ctx.json(new CompanyServices().findAll());
                });
                get("/branchOfficeList", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    System.out.println("USER logged: " + user.getEmail());
                    Company company = user.getCompany();
                    System.out.println("Nombre company: " + company.getName());

                    Set<BranchOffice> branchOfficeList;
                    if (user != null) {
                        if (company != null) {
                            branchOfficeList = user.getCompany().getBranchOfficeList();
                            ctx.json(branchOfficeList);
                        }
                    }
                });
                post("/branchOfficeEmployee", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    String email = user.getEmail();
                    if (user.hasRole(RoleApp.ROLE_EMPLOYEE)) {
                        BranchOffice branchOffice = BranchOfficeServices.getInstance().findBranchOfficeByUserEmployee(email);
                        ctx.json(branchOffice);

                    }
                });
                get("/findShelfByBranchOffice/:idBranchOffice", ctx -> {
                    String idBranchOffice = ctx.pathParam("idBranchOffice", String.class).get();
                    if (idBranchOffice != null) {
                        long idBranchOfficeAux = Long.parseLong(idBranchOffice);
                        BranchOffice branchOffice = ControllerCore.getInstance().findBranchOfficeById(idBranchOfficeAux);
                        List<Shelf> shelfList = ShelfServices.getInstance().findShelfByBranchOffice(branchOffice.getId());
                        //shelfList.stream().forEach(shelf -> shelfList);
                        ctx.json(shelfList);
                    }
                });
                get("/findContainerByBranchOffice/:idBranchOffice", ctx -> {
                    String idBranchOffice = ctx.pathParam("idBranchOffice", String.class).get();
                    if (idBranchOffice != null) {
                        System.out.println("Branch office: " + idBranchOffice);
                        long idBranchOfficeAux = Long.parseLong(idBranchOffice);
                        BranchOffice branchOffice = ControllerCore.getInstance().findBranchOfficeById(idBranchOfficeAux);
                        List<Container> containerList = ContainerServices.getInstance().findContainerfByBranchOffice(branchOffice.getId());
                        containerList.stream().forEach(container -> container.getId());
                        ctx.json(containerList);
                    }
                });
                get("/findShelfByDeviceId/:idShelf", ctx -> {
                    String deviceId = ctx.pathParam("idShelf");
                    Shelf shelf = ControllerCore.controllerCore.findShelfByDeviceId(deviceId);
                    ctx.json(shelf);
                });
                get("/shelfList", ctx -> {
                    List<Shelf> shelfList;
                    shelfList = new ShelfServices().findAll();
                    ctx.json(shelfList);
                });
                get("/containerList", ctx -> {
                    List<Container> containerList;
                    containerList = new ContainerServices().findAll();
                    ctx.json(containerList);
                });
                get("/getStatsByBranchOffice/:id/:date-input", ctx -> {
                    String date = ctx.pathParam("date-input", String.class).get();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateAux = dateFormat.parse(date);

                    String idBranchOffice = ctx.pathParam("id", String.class).get();
                    if (idBranchOffice != null) {
                        System.out.println("ID RECIBIDO: " + idBranchOffice);
                        long idBranchOfficeAux = Long.parseLong(idBranchOffice);
                        Stats stats = WasteDataServices.getInstance().wasteFruitsWeight(dateAux, idBranchOfficeAux);
                        ctx.json(stats);
                    }
                });
                get("/validIdentificationCard/:identificationCard", ctx -> {
                    String identificationCard = ctx.pathParam("identificationCard", String.class).get();
                    Person person = ControllerCore.getInstance().findPersonByIdentificationCard(identificationCard);
                    int foundOk = 200;
                    if (person != null) {
                        ctx.json(foundOk);
                    }
                });
                get("/express-sales/", ctx -> {
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    if (user.hasRole(RoleApp.ROLE_EMPLOYEE)) {
                        String email = user.getEmail();
                        BranchOffice branchOffice = BranchOfficeServices.getInstance().findBranchOfficeByUserEmployee(email);
                        if (branchOffice != null) {

                        }
                    }
                });
                get("/findBranchOfficeByEmployee/", ctx -> {
                    Map<String, Object> branchOfficeEmployee = new HashMap();
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    if (user != null) {
                        String email = user.getEmail();
                        BranchOffice branchOffice = BranchOfficeServices.getInstance().findBranchOfficeByUserEmployee(email);
                        if (branchOffice != null) {
                            ctx.json(branchOffice);
                        }
                    }
                });
                get("/display-forms/:idNotification", ctx -> {
                    Map<String, Object> formDisplay = new HashMap();
                    User user = UserServices.getInstance().find(ctx.sessionAttribute("user"));
                    long idNotification = Long.parseLong(ctx.pathParam("idNotification", String.class).get());
                    Notification notification = NotificationServices.getInstance().find(idNotification);
                    Company company = user.getCompany();
                    if (user != null) {
                        String email = user.getEmail();
                        BranchOffice branchOffice = BranchOfficeServices.getInstance().findBranchOfficeByUserEmployee(email);
                        if (branchOffice != null) {
                            if (notification != null) {
                                formDisplay.put("branchOffice: ", branchOffice);
                                formDisplay.put("fruitProduct", company.getFruitProductList());
                                formDisplay.put("fruitType", notification.getShelfData().getFruitType());
                                formDisplay.put("shelfId",notification.getShelfData().getShelf().getDeviceId());
                                formDisplay.put("overripeCant",notification.getShelfData().getCantOverripe());

                                for(FruitProduct fp: company.getFruitProductList()){
                                    formDisplay.put("discountPercentage",fp.getDiscountPercentage());
                                }
                                ctx.json(formDisplay);
                            }
                        }
                    }
                });
            });
        });
    }
}
