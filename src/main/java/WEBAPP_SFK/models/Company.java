package WEBAPP_SFK.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date registerDate;
    @OneToMany(mappedBy = "company",fetch = FetchType.EAGER)
    private Set<BranchOffice> branchOfficeList = new HashSet<>();

    public Company(){

    }

    public Company(String name, Date registerDate) {
        this.name = name;
        this.registerDate = registerDate;
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

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Set<BranchOffice> getBranchOfficeList() {
        return branchOfficeList;
    }

    public void setBranchOfficeList(Set<BranchOffice> branchOfficeList) {
        this.branchOfficeList = branchOfficeList;
    }
}
