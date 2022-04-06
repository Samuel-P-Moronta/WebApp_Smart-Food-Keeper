package WEBAPP_SFK.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class FruitProduct implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fruitType;
    private float price;
    private float discountPercentage;

    public FruitProduct() {
    }

    public FruitProduct(String fruitType, float price, float discountPercentage) {
        this.fruitType = fruitType;
        this.price = price;
        this.discountPercentage = discountPercentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFruitType() {
        return fruitType;
    }

    public void setFruitType(String fruitType) {
        this.fruitType = fruitType;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
