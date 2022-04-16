package WEBAPP_SFK.models;

import WEBAPP_SFK.models.enums.RoleApp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String registerDate;
    @Column(unique = true, nullable = false)
    private String rnc;
    //To avoid JSON infinite recursion issue 2/26/2022
    @JsonIgnore
    @OneToMany(mappedBy = "company",fetch = FetchType.EAGER)
    private Set<BranchOffice> branchOfficeList = new HashSet<>();
    @OneToMany(mappedBy = "company",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Notification> notificationList = new HashSet<>();
    @OneToMany(mappedBy = "company",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<FruitProduct> fruitProductList = new HashSet<>();

    public Company(){

    }

    public Company(String name, String rnc) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.registerDate = sdf.format(new Date());
        this.name = name;
        this.rnc = rnc;
    }

    public Company(String name, String registerDate, Set<BranchOffice> branchOfficeList, Set<Notification> notificationList) {
        this.name = name;
        this.registerDate = registerDate;
        this.branchOfficeList = branchOfficeList;
        this.notificationList = notificationList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public Set<BranchOffice> getBranchOfficeList() {
        return branchOfficeList;
    }

    public void setBranchOfficeList(Set<BranchOffice> branchOfficeList) {
        this.branchOfficeList = branchOfficeList;
    }

    public Set<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(Set<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public boolean hasThisBranchOffice(BranchOffice branchOffice){
        for(BranchOffice aux: branchOfficeList){
            if(aux.equals(branchOffice)){
                return true;
            }
        }
        return false;
    }

    public Set<FruitProduct> getFruitProductList() {
        return fruitProductList;
    }

    public void setFruitProductList(Set<FruitProduct> fruitProductList) {
        this.fruitProductList = fruitProductList;
    }

    public String getRnc() {
        return rnc;
    }

    public void setRnc(String rnc) {
        this.rnc = rnc;
    }
}
