package WEBAPP_SFK.models;

import javax.persistence.Column;
import javax.persistence.OneToOne;

public class ContainerDataJSON {
    private int measureId;
    private float weight;
    private String container;

    public ContainerDataJSON() {
    }

    public ContainerDataJSON(float weight, String container) {
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

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }
}
