package WEBAPP_SFK.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
    //To avoid JSON infinite recursion issue 2/26/2022
    @JsonIgnore
    @OneToMany(mappedBy = "company",fetch = FetchType.EAGER)
    private Set<BranchOffice> branchOfficeList = new HashSet<>();

    public Company(){

    }

    public Company(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.registerDate = sdf.format(new Date());
        this.name = name;
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
}
