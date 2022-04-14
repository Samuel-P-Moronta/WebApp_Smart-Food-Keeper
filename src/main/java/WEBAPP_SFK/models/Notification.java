package WEBAPP_SFK.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @CreationTimestamp
    private Date sendDate;
    @JsonIgnore
    // Carga Perezosa en JPA
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    private BranchOffice branchOffice;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Company company;
    private int type;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private ShelfData shelfData;
    private boolean status;


    public Notification() {
    }

    public Notification(String title, String description, Date sendDate, User user, BranchOffice branchOffice, Company company, int type, Boolean status) {
        this.title = title;
        this.description = description;
        this.sendDate = sendDate;
        this.user = user;
        this.branchOffice = branchOffice;
        this.company = company;
        this.type = type;
        this.status = status;
    }

    public Notification(String title, String description, Date sendDate, User user, BranchOffice branchOffice, Company company, int type, ShelfData shelfData, Boolean status) {
        this.title = title;
        this.description = description;
        this.sendDate = sendDate;
        this.user = user;
        this.branchOffice = branchOffice;
        this.company = company;
        this.type = type;
        this.shelfData = shelfData;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUserCredential() {
        return user;
    }

    public void setUserCredential(User user) {
        this.user = user;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ShelfData getShelfData() {
        return shelfData;
    }

    public void setShelfData(ShelfData shelfData) {
        this.shelfData = shelfData;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
