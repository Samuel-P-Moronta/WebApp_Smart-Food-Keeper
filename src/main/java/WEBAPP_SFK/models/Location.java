package WEBAPP_SFK.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Location implements Serializable {
    @Column(name = "ADDRESS")
    private Double address;
    @Column(name = "LONGITUDE")
    private String longitude;
    @Column(name = "LATITUDE")
    private Double latitude;

    public Location(){

    }


    public Location(Double address, String longitude, Double latitude) {
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Double getAddress() {
        return address;
    }

    public void setAddress(Double address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
