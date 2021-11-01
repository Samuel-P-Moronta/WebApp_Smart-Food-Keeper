package WEBAPP_SFK.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CONTAINER")
public class Container implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "REGISTER_DATE")
    private String registerDate;
    @OneToOne
    private BranchOffice branchOffice;

    public Container() {
    }

    public Container(String registerDate, BranchOffice branchOffice) {
        this.registerDate = registerDate;
        this.branchOffice = branchOffice;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }
}
