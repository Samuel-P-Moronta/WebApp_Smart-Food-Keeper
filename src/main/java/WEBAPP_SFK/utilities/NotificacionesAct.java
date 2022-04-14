package WEBAPP_SFK.utilities;

import WEBAPP_SFK.models.Notification;

public class NotificacionesAct {
    private Notification notification;
    private boolean activar;

    public NotificacionesAct(Notification notification, boolean activar) {
        this.notification = notification;
        this.activar = activar;
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
}
