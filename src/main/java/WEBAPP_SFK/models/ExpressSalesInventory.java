package WEBAPP_SFK.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "EXPRESS_SALES_INVENTORY")
public class ExpressSalesInventory {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private Date inspectionDate;
    private Boolean isExpress;
    private int quantity;
    @ManyToOne
    private BranchOffice branchOffice;
    @OneToOne
    private Shelf shelf;
    @OneToOne
    private User user;

    public ExpressSalesInventory() {
    }

    public ExpressSalesInventory(Date inspectionDate, Boolean isExpress, int quantity, BranchOffice branchOffice, Shelf shelf, User user) {
        this.inspectionDate = inspectionDate;
        this.isExpress = isExpress;
        this.quantity = quantity;
        this.branchOffice = branchOffice;
        this.shelf = shelf;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getExpress() {
        return isExpress;
    }

    public void setExpress(Boolean express) {
        isExpress = express;
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
}
