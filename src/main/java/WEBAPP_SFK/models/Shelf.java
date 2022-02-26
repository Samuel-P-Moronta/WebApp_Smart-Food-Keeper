package WEBAPP_SFK.models;

import WEBAPP_SFK.models.enums.StatusShelf;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Shelf implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String deviceId;
    @CreationTimestamp
    private Date registerDate;
    @JsonIgnore
    @ManyToOne
    private BranchOffice branchOffice;

    public Shelf(){

    }
    public Shelf(Date registerDate, BranchOffice branchOffice) {
        this.registerDate = registerDate;
        this.branchOffice = branchOffice;

    }

    public String getDevice_name() {
        return deviceId;
    }

    public void setDevice_name(String device_name) {
        this.deviceId = device_name;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

}
