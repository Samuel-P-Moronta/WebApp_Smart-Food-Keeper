package WEBAPP_SFK.models;

import javax.persistence.Column;
import javax.persistence.OneToOne;

public class ContainerDataJSON {
    private int measureId;
    private float weight;
    private int container;
    private int statusCode;

    public ContainerDataJSON() {
    }

    public ContainerDataJSON(float weight, int container,int statusCode) {
        this.weight = weight;
        this.container = container;
        this.statusCode = statusCode;
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

    public int getContainer() {
        return container;
    }

    public void setContainer(int container) {
        this.container = container;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
