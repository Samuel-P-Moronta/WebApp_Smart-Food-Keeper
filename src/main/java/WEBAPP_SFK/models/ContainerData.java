package WEBAPP_SFK.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class ContainerData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int measureId;
    private float weight;
    private String currentSampleDate;
    private String containerId;



    public ContainerData() {
    }

    public ContainerData(float weight, String containerId) {
        this.weight = weight;
        this.containerId = containerId;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.currentSampleDate = sdf.format(new Date());
    }

    public int getMeasureId() {
        return measureId;
    }

    public void setMeasureId(int measureId) {
        this.measureId = measureId;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getCurrentSampleDate() {
        return currentSampleDate;
    }

    public void setCurrentSampleDate(String currentSampleDate) {
        this.currentSampleDate = currentSampleDate;
    }
}
