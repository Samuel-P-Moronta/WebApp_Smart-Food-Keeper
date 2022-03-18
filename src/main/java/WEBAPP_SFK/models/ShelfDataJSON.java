package WEBAPP_SFK.models;

import javax.persistence.Column;
import java.util.Date;

public class ShelfDataJSON {
    private int measureId;
    private Float temperature;
    private Float humidity;
    private int fruitCant;
    private String fruitType;
    private int cantOverripe;
    private int cantRipe;
    private int cantUnripe;
    private Date currentSampleDate;
    private String deviceId;


    public ShelfDataJSON() {
    }

    public ShelfDataJSON(Float temperature, Float humidity, int fruitCant, String fruitType, int cantOverripe, int cantRipe, int cantUnripe, String deviceId) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.fruitCant = fruitCant;
        this.fruitType = fruitType;
        this.cantOverripe = cantOverripe;
        this.cantRipe = cantRipe;
        this.cantUnripe = cantUnripe;
        this.deviceId = deviceId;
    }

    public int getMeasureId() {
        return measureId;
    }

    public void setMeasureId(int measureId) {
        this.measureId = measureId;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
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

    public Date getCurrentSampleDate() {
        return currentSampleDate;
    }

    public void setCurrentSampleDate(Date currentSampleDate) {
        this.currentSampleDate = currentSampleDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
