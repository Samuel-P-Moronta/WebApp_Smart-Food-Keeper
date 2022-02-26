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
    @CreationTimestamp
    private Date currentSampleDate;
    //Nivel de llenado del safacon
    // MINIMO = 1 MEDIO = 2 LLENO = 3 COMPLETAMENTE VACIO = 0
    private int statusCode;



    public ContainerData() {
    }

    public ContainerData(float weight, Container container, int statusCode) {
        this.weight = weight;
        this.container = container;
        this.currentSampleDate = currentSampleDate;
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

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


}
