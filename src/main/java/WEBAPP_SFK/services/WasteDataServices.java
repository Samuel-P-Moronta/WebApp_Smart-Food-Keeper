package WEBAPP_SFK.services;


import WEBAPP_SFK.models.WasteData;
import WEBAPP_SFK.services.connect.DataBaseRepository;

public class WasteDataServices extends DataBaseRepository<WasteData> {
    private static WasteDataServices wasteDataServices;

    public WasteDataServices() {
        super(WasteData.class);
    }

    public static WasteDataServices getInstance() {
        if(wasteDataServices==null){
            wasteDataServices = new WasteDataServices();
        }
        return wasteDataServices;
    }
}
