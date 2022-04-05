package WEBAPP_SFK.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class WasteData implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float wasteData;
    //@CreationTimestamp
    private Date sendDate;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name="containerId", nullable=false)
    private Container container;

    public WasteData() {

    }
    public WasteData(float wasteData, Date sendDate) {
        this.wasteData = wasteData;
        this.sendDate = sendDate;
    }

    public WasteData(float wasteData, Date sendDate, Container container) {
        this.wasteData = wasteData;
        this.sendDate = sendDate;
        this.container = container;
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

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
