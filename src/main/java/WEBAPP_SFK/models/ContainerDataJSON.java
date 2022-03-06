package WEBAPP_SFK.models;

import javax.persistence.Column;
import javax.persistence.OneToOne;

public class ContainerDataJSON {
    private int measureId;
    private float weight;
    private String containerId;
    private String currentSampleDate;

    public ContainerDataJSON() {
    }

    public ContainerDataJSON(float weight, String containerId) {
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

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }
}
