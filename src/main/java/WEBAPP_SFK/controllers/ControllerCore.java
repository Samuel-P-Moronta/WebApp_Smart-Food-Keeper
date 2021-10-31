package WEBAPP_SFK.controllers;

import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.models.User;
import WEBAPP_SFK.services.PersonServices;
import WEBAPP_SFK.services.ShelfDataServices;
import WEBAPP_SFK.services.ShelfServices;
import WEBAPP_SFK.services.UserServices;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ControllerCore {
    public static ControllerCore controllerCore;
    public static final PersonServices personServices = new PersonServices();
    public static final UserServices userServices = new UserServices();
    public static final ShelfServices shelfServices = new ShelfServices();
    public static final ShelfDataServices shelfDataServices = new ShelfDataServices();





    public ControllerCore() {
    }
    /* Singleton pattern */
    public static ControllerCore getInstance() {
        if (controllerCore == null) {
            controllerCore = new ControllerCore();
        }
        return controllerCore;
    }
    public boolean addPerson(Person p){
        return personServices.create(p);
    }
    public boolean addUser(User u){
        return userServices.create(u);
    }
    public boolean addShelf(Shelf sh){
        return shelfServices.create(sh);
    }
    public boolean addShelfData(ShelfData sh){
        return shelfDataServices.create(sh);
    }
    public Shelf getShelfByDeviceName(String shelf){return shelfServices.findById(shelf);}

    public List<ShelfData> getAllData(String deviceName){
        return shelfDataServices.getAllShelfData(deviceName);
    }

    public Shelf createFakeShelf(){

        Shelf s = new Shelf("SH001","2021-30-10 02:51:02");
        ShelfDataServices.getInstance().create(s);
        return s;
    }
    public static void createFakeShelfData() throws InterruptedException {

        for(int i = 0; i < 10; ++i){
            Double temperature = (Double) Math.floor(Math.random() * (40 - 20 + 1) + 20);
            Double humidity = (Double) Math.floor(Math.random() * (40 - 20 + 1) + 20);
            int cantFrutas = 4;
            int percentageOverripe = 25;
            int percentageRipe = 50;
            int percentageUnripe = 25;
            Date currentSampleDate = new Date(System.currentTimeMillis());

            Thread.sleep(4000);
            Shelf sf = new Shelf("SH001", "2021-30-10 02:51:02");
            ShelfData sd = new ShelfData(temperature, humidity, cantFrutas, "PINEAPPLE", percentageOverripe, percentageRipe, percentageUnripe, currentSampleDate, sf);
            ControllerCore.getInstance().addShelfData(sd);

        }
    }
    public void createFakeDataToBD() throws InterruptedException {
        //createFakeShelf();

        createFakeShelfData();
    }
    public List<ShelfData> listShelfData(){
        return shelfDataServices.findAll();
    }
    public List<ShelfData> getShelfDataByShelf(String deviceName){
        List<ShelfData> sh1 = new ArrayList<ShelfData>();
        for(ShelfData s: listShelfData()){
            if(s.getShelf().getDevice_name().equals(deviceName)){
                sh1.add(s);
            }
        }
        return sh1;
    }

}
