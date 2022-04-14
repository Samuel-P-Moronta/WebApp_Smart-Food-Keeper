package WEBAPP_SFK.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Form implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int idNotificationSelect;
    private int shelfId;
    private String fruitType;
    private int discountPercentage;
    private int quantity;
    private String inspectionType;
    private Date registerDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private BranchOffice branchOffice;

    public Form() {
    }

    public Form(int idNotificationSelect, int shelfId, String fruitType, int discountPercentage, int quantity, String inspectionType, Date registerDate, BranchOffice branchOffice) {
        this.idNotificationSelect = idNotificationSelect;
        this.shelfId = shelfId;
        this.fruitType = fruitType;
        this.discountPercentage = discountPercentage;
        this.quantity = quantity;
        this.inspectionType = inspectionType;
        this.registerDate = registerDate;
        this.branchOffice = branchOffice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIdNotificationSelect() {
        return idNotificationSelect;
    }

    public void setIdNotificationSelect(int idNotificationSelect) {
        this.idNotificationSelect = idNotificationSelect;
    }

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public String getFruitType() {
        return fruitType;
    }

    public void setFruitType(String fruitType) {
        this.fruitType = fruitType;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }
}
