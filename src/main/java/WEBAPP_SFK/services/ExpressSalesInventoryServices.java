package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Client;
import WEBAPP_SFK.models.ExpressSalesInventory;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import java.util.HashMap;
import java.util.Map;

public class ExpressSalesInventoryServices extends DataBaseRepository<ExpressSalesInventory> {
    private static ExpressSalesInventoryServices instance;

    public ExpressSalesInventoryServices() {
        super(ExpressSalesInventory.class);
    }

    public static ExpressSalesInventoryServices getInstance() {
        if(instance==null){
            instance = new ExpressSalesInventoryServices();
        }
        return instance;
    }

    public void lastDataConsulted(){
        Map<String, Object> lastData = new HashMap();

    }
}
