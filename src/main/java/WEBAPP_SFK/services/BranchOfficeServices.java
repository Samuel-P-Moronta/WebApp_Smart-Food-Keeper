package WEBAPP_SFK.services;

import WEBAPP_SFK.models.BranchOffice;
import WEBAPP_SFK.models.Organization;
import WEBAPP_SFK.services.connect.DataBaseRepository;

public class BranchOfficeServices extends DataBaseRepository<BranchOffice> {
    private static BranchOfficeServices instance;

    public BranchOfficeServices() {

        super(BranchOffice.class);
    }

    public static BranchOfficeServices getInstance() {
        if(instance==null){
            instance = new BranchOfficeServices();
        }
        return instance;
    }
}
