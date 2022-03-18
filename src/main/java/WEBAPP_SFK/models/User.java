package WEBAPP_SFK.models;

import WEBAPP_SFK.models.enums.RoleApp;
import io.javalin.core.security.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
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
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<Notification> notificationList = new HashSet<>();
    @ManyToOne
    private BranchOffice branchOffice;
    @ManyToOne
    private Company company;


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

    public User(String email, String password, Set<RoleApp> rolesList,Company company) {
        this.email = email;
        this.password = password;
        this.rolesList = rolesList;
        this.company = company;
    }

    public User(String email, String password, Set<RoleApp> rolesList, Set<Notification> notificationList, BranchOffice branchOffice, Company company) {
        this.email = email;
        this.password = password;
        this.rolesList = rolesList;
        this.notificationList = notificationList;
        this.branchOffice = branchOffice;
        this.company = company;
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

    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean hasRole(Role role){
        for (RoleApp aux:rolesList){
            if(aux.equals(role)){
                return true;
            }
        }
        return false;
    }

    public Set<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(Set<Notification> notificationList) {
        this.notificationList = notificationList;
    }
}
