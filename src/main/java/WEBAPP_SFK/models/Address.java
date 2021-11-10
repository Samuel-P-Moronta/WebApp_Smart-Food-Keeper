package WEBAPP_SFK.models;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String direction;
    private String city;

    public Address() {
    }

    public Address(String direction, String city) {
        this.direction = direction;
        this.city = city;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
