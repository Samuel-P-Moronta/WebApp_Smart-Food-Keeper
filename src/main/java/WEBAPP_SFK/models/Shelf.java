package WEBAPP_SFK.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "SHELF")
public class Shelf implements Serializable {
    @Id
    @Column(name = "DEVICE_NAME",unique = true)
    private String device_name;
    @CreationTimestamp
    private Date registerDate;

    public Shelf(){

    }

    public Shelf(String device_name, Date registerDate) {
        this.device_name = device_name;
        this.registerDate = registerDate;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}
