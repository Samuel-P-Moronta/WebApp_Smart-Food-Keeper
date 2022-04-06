package WEBAPP_SFK.models;

import WEBAPP_SFK.models.enums.RoleApp;
import io.javalin.core.security.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class FruitProduct implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fruitType;
    private float price;
    private float discountPercentage;
    private Date registerDate;
    @ManyToOne
    private Company company;


    public FruitProduct() {
    }

    public FruitProduct(String fruitType, float discountPercentage, Date registerDate, Company company) {
        this.fruitType = fruitType;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.registerDate = registerDate;
        this.company = company;
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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
