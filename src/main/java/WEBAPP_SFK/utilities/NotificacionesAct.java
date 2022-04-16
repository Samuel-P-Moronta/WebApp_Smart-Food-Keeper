package WEBAPP_SFK.utilities;

import WEBAPP_SFK.models.Notification;

public class NotificacionesAct {
    private Notification notification;
    private boolean activar;
    private boolean notiType;


    public NotificacionesAct(Notification notification, boolean activar, boolean notiType) {
        this.notification = notification;
        this.activar = activar;
        this.notiType = notiType;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public boolean isActivar() {
        return activar;
    }

    public void setActivar(boolean activar) {
        this.activar = activar;
    }

    public boolean isNotiType() {
        return notiType;
    }

    public void setNotiType(boolean notiType) {
        this.notiType = notiType;
    }
}
