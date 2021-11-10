package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Container;
import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.services.connect.DataBaseRepository;

public class ContainerServices  extends DataBaseRepository<Container> {
    private static ContainerServices instance;

    public ContainerServices() {
        super(Container.class);
    }

    public static ContainerServices getInstance() {
        if(instance==null){
            instance = new ContainerServices();
        }
        return instance;
    }

}
