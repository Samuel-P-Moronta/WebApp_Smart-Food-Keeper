package WEBAPP_SFK.models.enums;

public enum NotificationStatus {
    MADUREZ(1,"Existen frutas en estado muy maduras"),
    SUMINISTRO(2,"Necesitas abastecer el estante"),
    TEMPERATURA(3,"La temperatura ha excedido el limite permitido"),
    HUMEDAD(4,"La humedad ha excedido el limite permitido");


    private Integer id;
    private String message;

    NotificationStatus(Integer id, String message){
        this.id = id;
        this.message = message;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
