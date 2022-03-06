package WEBAPP_SFK.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class ContainerData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int measureId;
    private float weight;
    @OneToOne
    private Container container;
    private Date currentSampleDate;



    public ContainerData() {
    }

    public ContainerData(float weight, Container container, Date currentSampleDate) {
        this.weight = weight;
        this.container = container;
        this.currentSampleDate = currentSampleDate;
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

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public Date getCurrentSampleDate() {
        return currentSampleDate;
    }

    public void setCurrentSampleDate(Date currentSampleDate) {
        this.currentSampleDate = currentSampleDate;
    }
}
