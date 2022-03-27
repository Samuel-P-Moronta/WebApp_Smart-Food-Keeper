package WEBAPP_SFK.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class WasteData{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float wasteData;
    @CreationTimestamp
    private Date sendDate;

    public WasteData() {

    }

    public WasteData(float wasteData, Date sendDate) {
        this.wasteData = wasteData;
        this.sendDate = sendDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getWasteData() {
        return wasteData;
    }

    public void setWasteData(float wasteData) {
        this.wasteData = wasteData;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
