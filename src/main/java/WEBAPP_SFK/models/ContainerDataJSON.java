package WEBAPP_SFK.models;

import javax.persistence.Column;
import javax.persistence.OneToOne;

public class ContainerDataJSON {
    private int measureId;
    private float weight;
    private long containerId;

    public ContainerDataJSON() {
    }

    public ContainerDataJSON(float weight, long containerId) {
        this.weight = weight;
        this.containerId = containerId;
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

    public long getContainerId() {
        return containerId;
    }

    public void setContainerId(long containerId) {
        this.containerId = containerId;
    }
}
