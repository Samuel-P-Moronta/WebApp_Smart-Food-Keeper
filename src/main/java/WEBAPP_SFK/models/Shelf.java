package WEBAPP_SFK.models;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "SHELF")
public class Shelf implements Serializable {
    @Id
    @Column(name = "DEVICE_NAME")
    private String device_name;
    @Column(name = "REGISTER_DATE")
    private String registerDate;

    public Shelf(){

    }


    public Shelf(String device_name, String registerDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.registerDate = sdf.format(new Date());
        this.device_name = device_name;
        this.registerDate = registerDate;
    }
    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

}
