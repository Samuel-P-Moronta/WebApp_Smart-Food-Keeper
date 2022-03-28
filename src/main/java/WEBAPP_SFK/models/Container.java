package WEBAPP_SFK.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
public class Container implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String containerId;
    private String registerDate;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private BranchOffice branchOffice;
    @JsonManagedReference
    @OneToMany(mappedBy = "container",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<WasteData> wasteDataList;

    public Container(BranchOffice branchOffice) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.registerDate = sdf.format(new Date());
        this.branchOffice = branchOffice;
    }

    public String getId() {
        return containerId;
    }

    public void setId(String id) {
        this.containerId = containerId;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }
    public BranchOffice getBranchOffice() {

        return branchOffice;
    }

    public Container() {
    }

    public Set<WasteData> getWasteDataList() {
        return wasteDataList;
    }

    public void setWasteDataList(Set<WasteData> wasteDataList) {
        this.wasteDataList = wasteDataList;
    }
}
