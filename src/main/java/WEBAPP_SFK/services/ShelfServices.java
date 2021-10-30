package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.models.Shelf;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.services.connect.DataBaseRepository;

public class ShelfServices extends DataBaseRepository<Shelf> {
    public ShelfServices() {
        super(Shelf.class);
    }
}
