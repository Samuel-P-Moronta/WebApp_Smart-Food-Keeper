package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Client;
import WEBAPP_SFK.models.Company;
import WEBAPP_SFK.services.connect.DataBaseRepository;

public class ClientServices extends DataBaseRepository<Client> {

    public ClientServices() {
        super(Client.class);
    }

}
