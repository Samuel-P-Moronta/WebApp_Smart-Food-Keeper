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
    @Column(name = "TEMPERATURE")
    private Double temperature;
    @Column(name = "HUMIDITY")
    private Double humidity;
    @Column(name = "FRUIT_CANT")
    private int fruitCant;
    @Column(name = "FRUIT_TYPE")
    private String fruitType;
    @Column(name = "PERCENTAGE_OVERRIPE")
    private int percentageOverripe;
    @Column(name = "PERCENTAGE_RIPE")
    private int percentageRipe;
    @Column(name = "PERCENTAGE_UNRIPE")
    private int percentage_unripe;
    @Column(name = "CURRENT_SAMPLE_DATE")
    private String currentSampleDate;


    @OneToOne
    private Shelf shelf;

    public ShelfData(){

    }


    public ShelfData(Double temperature, Double humidity,
                     int fruitCant, String fruitType,
                     int percentageOverripe, int percentageRipe,
                     int percentage_unripe, String currentSampleDate,Shelf shelf) {

        this.temperature = temperature;
        this.humidity = humidity;
        this.fruitCant = fruitCant;
        this.fruitType = fruitType;
        this.percentageOverripe = percentageOverripe;
        this.percentageRipe = percentageRipe;
        this.percentage_unripe = percentage_unripe;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.currentSampleDate = sdf.format(new Date());
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

    public int getPercentage_unripe() {
        return percentage_unripe;
    }

    public void setPercentage_unripe(int percentage_unripe) {
        this.percentage_unripe = percentage_unripe;
    }

    public String getCurrentSampleDate() {
        return currentSampleDate;
    }

    public void setCurrentSampleDate(String currentSampleDate) {
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
