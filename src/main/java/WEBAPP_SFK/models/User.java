package WEBAPP_SFK.models;

import WEBAPP_SFK.models.enums.RoleApp;
import io.javalin.core.security.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Embeddable
public class User implements Serializable {
    @Id
    @Column(name = "EMAIL", unique = true)
    private String email;
    private String password;
    // Carga en linea
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<RoleApp> rolesList;
    @OneToMany(mappedBy = "user")
    private Collection<Notification> notifications;
    @ManyToOne
    private BranchOffice branchOffice;


    public User() {
    }

    public User(String email, String password, Set<RoleApp> rolesList) {
        this.email = email;
        this.password = password;
        this.rolesList = rolesList;
    }

    public User(String email, String password, Set<RoleApp> rolesList, BranchOffice branchOffice) {
        this.email = email;
        this.password = password;
        this.rolesList = rolesList;
        this.branchOffice = branchOffice;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleApp> getRolesList() {
        return rolesList;
    }

    public void setRolesList(Set<RoleApp> rolesList) {
        this.rolesList = rolesList;
    }

    public Collection<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Collection<Notification> notifications) {
        this.notifications = notifications;
    }

    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

    public boolean hasRole(Role role){
        for (RoleApp aux:rolesList){
            if(aux.equals(role)){
                return true;
            }
        }
        return false;
    }

}
