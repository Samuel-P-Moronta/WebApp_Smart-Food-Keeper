package WEBAPP_SFK.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CONTAINER_DATA")
public class ContainerData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int measureId;
    @Column(name = "WEIGHT")
    private float weight;
    @OneToOne
    private Container container;


    public ContainerData() {
    }

    public ContainerData(float weight, Container container) {
        this.weight = weight;
        this.container = container;
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
}
