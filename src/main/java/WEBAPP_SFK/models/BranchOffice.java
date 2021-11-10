package WEBAPP_SFK.models;

import org.hibernate.annotations.CreationTimestamp;

import javax.naming.Name;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BRANCH_OFFICE")
public class BranchOffice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Address address;
    @CreationTimestamp
    private Date registerDate;
    @ManyToOne
    private Organization organization;

    public BranchOffice(){

    }


    public BranchOffice(Address address, Date registerDate, Organization organization) {
        this.address = address;
        this.registerDate = registerDate;
        this.organization = organization;
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
