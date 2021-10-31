package WEBAPP_SFK.models;

import WEBAPP_SFK.services.connect.DataBaseRepository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BRANCH_OFFICE")
public class BranchOffice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "REGISTER_DATE")
    private Date registerDate;
    @ManyToOne
    private Organization organization;
    @Embedded
    private Location location;

    public BranchOffice() {

    }
    public BranchOffice(String description, Date registerDate, Organization organization, Location location) {
        this.description = description;
        this.registerDate = registerDate;
        this.organization = organization;
        this.location = location;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
