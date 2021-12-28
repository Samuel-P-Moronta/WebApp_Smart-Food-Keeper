package WEBAPP_SFK.models;

import javax.persistence.Column;
import java.util.Date;

public class ShelfDataJSON {
    private int measureId;
    private Double temperature;
    private Double humidity;
    private int fruitCant;
    private String fruitType;
    private int cantOverripe;
    private int cantRipe;
    private int cantUnripe;
    private Date currentSampleDate;
    private String Shelf;

    public ShelfDataJSON() {
    }

    public ShelfDataJSON(Double temperature, Double humidity, int fruitCant, String fruitType, int cantOverripe, int cantRipe, int cantUnripe, Date currentSampleDate, String shelf) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.fruitCant = fruitCant;
        this.fruitType = fruitType;
        this.cantOverripe = cantOverripe;
        this.cantRipe = cantRipe;
        this.cantUnripe = cantUnripe;
        this.currentSampleDate = currentSampleDate;
        Shelf = shelf;
    }

    public int getMeasureId() {
        return measureId;
    }

    public void setMeasureId(int measureId) {
        this.measureId = measureId;
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



    public Date getCurrentSampleDate() {
        return currentSampleDate;
    }

    public void setCurrentSampleDate(Date currentSampleDate) {
        this.currentSampleDate = currentSampleDate;
    }

    public String getShelf() {
        return Shelf;
    }

    public void setShelf(String shelf) {
        Shelf = shelf;
    }

    public int getCantOverripe() {
        return cantOverripe;
    }

    public void setCantOverripe(int cantOverripe) {
        this.cantOverripe = cantOverripe;
    }

    public int getCantRipe() {
        return cantRipe;
    }

    public void setCantRipe(int cantRipe) {
        this.cantRipe = cantRipe;
    }

    public int getCantUnripe() {
        return cantUnripe;
    }

    public void setCantUnripe(int cantUnripe) {
        this.cantUnripe = cantUnripe;
    }
}
