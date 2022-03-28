package WEBAPP_SFK.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BranchOffice {
    //private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Address address;
    private String registerDate;
    @ManyToOne
    private Company company;
    @OneToMany(mappedBy = "branchOffice",fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference
    private Set<Shelf> shelfList = new HashSet<>();
    @OneToMany(mappedBy = "branchOffice",fetch = FetchType.EAGER)
    @Column(nullable = true)
    @JsonManagedReference
    private Set<Container> containerList = new HashSet<>();

    public BranchOffice(){

    }
    public BranchOffice(Address address, Company company) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.registerDate = sdf.format(new Date());
        this.address = address;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Shelf> getShelfList() {
        return shelfList;
    }
    public void setShelfList(Set<Shelf> shelfList) {
        this.shelfList = shelfList;
    }
    public Set<Container> getContainerList() {
        return containerList;
    }
    public void setContainerList(Set<Container> containerList) {
        this.containerList = containerList;
    }
}
