package WEBAPP_SFK.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BranchOffice implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Address address;
    @CreationTimestamp
    private Date registerDate;
    @ManyToOne
    private Company company;
    @OneToMany(mappedBy = "branchOffice",fetch = FetchType.EAGER)
    private Set<Shelf> shelfList = new HashSet<>();
    @OneToMany(mappedBy = "branchOffice",fetch = FetchType.EAGER)
    private Set<Container> containerList = new HashSet<>();

    public BranchOffice(){

    }


    public BranchOffice(Address address, Date registerDate, Company company) {
        this.address = address;
        this.registerDate = registerDate;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Company getOrganization() {
        return company;
    }

    public void setOrganization(Company company) {
        this.company = company;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
