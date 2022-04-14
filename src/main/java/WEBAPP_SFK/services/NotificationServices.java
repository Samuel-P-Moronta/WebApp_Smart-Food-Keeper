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
    public Notification findNotificationByStatus(boolean status, int type, User user) {
        EntityManager em = getEntityManager();
        String sql = "";
        sql += " SELECT N from Notification N WHERE N.status = :status and N.type =:type and user.email =:email";

        Query query = em.createQuery(sql, Notification.class);
        query.setParameter("status", status);
        query.setParameter("type", type);
        query.setParameter("email",user.getEmail());

        List<Notification> notificationList = query.getResultList();
        if (notificationList.size() > 0) {
            return notificationList.get(0);
        }
        return null;
    }


    public List<Notification> findNotificationByUserEmail(String email) {
        EntityManager em = getEntityManager();
        String sql = "SELECT N from Notification N WHERE user.email =:email";
        Query query = em.createQuery(sql, Notification.class);
        query.setParameter("email",email);
        return query.getResultList();
    }
    public List<Notification> findNotificationEmail(String email) {
        EntityManager em = getEntityManager();
        String sql = "SELECT * FROM Notification N " +
                "WHERE N.id IN (SELECT N.id FROM NOTIFICATION  " +
                "INNER JOIN BranchOffice B ON N.branchOffice_id = B.id " +
                "INNER JOIN Company C ON B.company_id = C.id " +
                "INNER JOIN User U on C.id = U.company_id WHERE U.email = :email)";
        Query query = em.createNativeQuery(sql, Notification.class);
        query.setParameter("email",email);
        return query.getResultList();
    }
}
