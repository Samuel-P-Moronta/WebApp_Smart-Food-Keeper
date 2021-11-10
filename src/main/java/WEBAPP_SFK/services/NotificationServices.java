package WEBAPP_SFK.services;

import WEBAPP_SFK.models.Notification;
import WEBAPP_SFK.models.Person;
import WEBAPP_SFK.models.ShelfData;
import WEBAPP_SFK.services.connect.DataBaseRepository;

public class NotificationServices extends DataBaseRepository<Notification> {
    private static NotificationServices instance;

    public NotificationServices() {
        super(Notification.class);
    }
    public static NotificationServices getInstance() {
        if(instance==null){
            instance = new NotificationServices();
        }
        return instance;
    }
}
