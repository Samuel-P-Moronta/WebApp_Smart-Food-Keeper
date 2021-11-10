package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Container;
import WEBAPP_SFK.models.ContainerData;
import WEBAPP_SFK.services.connect.DataBaseRepository;

public class ContainerDataServices extends DataBaseRepository<ContainerData> {
    private static ContainerDataServices instance;

    public ContainerDataServices() {
        super(ContainerData.class);
    }

    public static ContainerDataServices getInstance() {
        if(instance==null){
            instance = new ContainerDataServices();
        }
        return instance;
    }

}
