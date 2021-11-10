package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Notification;
import WEBAPP_SFK.models.Organization;
import WEBAPP_SFK.services.connect.DataBaseRepository;

public class OrganizationServices extends DataBaseRepository<Organization> {
    private static OrganizationServices instance;

    public OrganizationServices() {
        super(Organization.class);
    }
    public static OrganizationServices getInstance() {
        if(instance==null){
            instance = new OrganizationServices();
        }
        return instance;
    }
}
