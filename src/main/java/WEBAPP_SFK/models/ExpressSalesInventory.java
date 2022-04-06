package WEBAPP_SFK.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "EXPRESS_SALES_INVENTORY")
public class ExpressSalesInventory implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date inspectionDate;
    private int quantity;
    @ManyToOne
    private BranchOffice branchOffice;
    @OneToOne
    private Shelf shelf;
    @OneToOne
    private User user;
    private String fruitType;
    private float discountPercentage;

    public ExpressSalesInventory() {
    }
    public ExpressSalesInventory(Date inspectionDate, int quantity, BranchOffice branchOffice, Shelf shelf, User user, String fruitType, float discountPercentage) {
        this.inspectionDate = inspectionDate;
        this.quantity = quantity;
        this.branchOffice = branchOffice;
        this.shelf = shelf;
        this.user = user;
        this.fruitType = fruitType;
        this.discountPercentage = discountPercentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getFruitType() {
        return fruitType;
    }

    public void setFruitType(String fruitType) {
        this.fruitType = fruitType;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
