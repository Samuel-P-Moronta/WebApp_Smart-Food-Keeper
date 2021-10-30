package WEBAPP_SFK.models;

import javax.persistence.Column;
import java.util.Date;

public class ShelfDataJSON {
    private int measureId;
    private Double temperature;
    private Double humidity;
    private int fruitCant;
    private String fruitType;
    private int percentageOverripe;
    private int percentageRipe;
    private int percentageUnripe;
    private Date currentSampleDate;
    private String Shelf;

    public ShelfDataJSON() {
    }

    public ShelfDataJSON(Double temperature, Double humidity, int fruitCant, String fruitType, int percentageOverripe, int percentageRipe, int percentageUnripe, Date currentSampleDate, String shelf) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.fruitCant = fruitCant;
        this.fruitType = fruitType;
        this.percentageOverripe = percentageOverripe;
        this.percentageRipe = percentageRipe;
        this.percentageUnripe = percentageUnripe;
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

    public int getPercentageOverripe() {
        return percentageOverripe;
    }

    public void setPercentageOverripe(int percentageOverripe) {
        this.percentageOverripe = percentageOverripe;
    }

    public int getPercentageRipe() {
        return percentageRipe;
    }

    public void setPercentageRipe(int percentageRipe) {
        this.percentageRipe = percentageRipe;
    }

    public int getPercentageUnripe() {
        return percentageUnripe;
    }

    public void setPercentageUnripe(int percentageUnripe) {
        this.percentageUnripe = percentageUnripe;
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
}
