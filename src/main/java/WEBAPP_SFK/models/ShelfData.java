package WEBAPP_SFK.models;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "SHELF_DATA")
public class ShelfData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int measureId;
    private Double temperature;
    private Double humidity;
    private int fruitCant;
    private String fruitType;
    private int cantOverripe;
    private int cantRipe;
    private int cantUnripe;
    private Date currentSampleDate;


    @OneToOne
    private Shelf shelf;
    public ShelfData(){

    }

    public ShelfData(Double temperature, Double humidity,
                     int fruitCant, String fruitType,
                     int cantOverripe, int cantRipe,
                     int cantUnripe, Date currentSampleDate,Shelf shelf) {

        this.temperature = temperature;
        this.humidity = humidity;
        this.fruitCant = fruitCant;
        this.fruitType = fruitType;
        this.cantOverripe = cantOverripe;
        this.cantRipe = cantRipe;
        this.cantUnripe = cantUnripe;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.currentSampleDate = currentSampleDate;
        this.shelf = shelf;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public int getFruitCant() {
        return fruitCant;
    }

    public void setFruitCant(int fruitCant) {
        this.fruitCant = fruitCant;
    }

    public String getFruitType() {
        return fruitType;
    }

    public void setFruitType(String fruitType) {
        this.fruitType = fruitType;
    }

    public int getCantOverripe() {
        return cantOverripe;
    }

    public void setCantOverripe(int percentageOverripe) {
        this.cantOverripe = percentageOverripe;
    }

    public int getCantRipe() {
        return cantRipe;
    }

    public void setCantRipe(int percentageRipe) {
        this.cantRipe = percentageRipe;
    }

    public int getCantUnripe() {
        return cantUnripe;
    }

    public void setCantUnripe(int percentage_unripe) {
        this.cantUnripe = percentage_unripe;
    }

    public Date getCurrentSampleDate() {
        return currentSampleDate;
    }

    public void setCurrentSampleDate(Date currentSampleDate) {
        this.currentSampleDate = currentSampleDate;
    }
    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }
    public int getMeasureId() {
        return measureId;
    }

    public void setMeasureId(int measureId) {
        this.measureId = measureId;
    }




}
