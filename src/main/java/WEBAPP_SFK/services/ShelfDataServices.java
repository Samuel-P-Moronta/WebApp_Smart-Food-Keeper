package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.services.connect.DataBaseRepository;

public class ShelfDataServices extends DataBaseRepository<ShelfData> {
    public ShelfDataServices() {
        super(ShelfData.class);
    }
}
