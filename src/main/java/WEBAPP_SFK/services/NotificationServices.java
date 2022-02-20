package WEBAPP_SFK.services;

import WEBAPP_SFK.models.*;
import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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

    public Notification findNotificationByType(int type, User user) {
        EntityManager em = getEntityManager();
        String sql = "";
        sql += " SELECT N from Notification N WHERE N.type = :type and user.email =:email";

        Query query = em.createQuery(sql, Notification.class);
        query.setParameter("type", type);
        query.setParameter("email",user.getEmail());
        List<Notification> notificationList = query.getResultList();
        if (notificationList.size() > 0) {
            return notificationList.get(0);
        }
        return null;
    }


}
