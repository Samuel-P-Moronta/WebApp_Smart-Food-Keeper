package WEBAPP_SFK.models;

import WEBAPP_SFK.models.enums.RoleApp;

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
    @JoinColumn(name = "ID_PERSON")
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Person person;
    @ManyToOne
    private BranchOffice branchOffice;


    public User() {
    }

    public User(String email, String password, Set<RoleApp> rolesList, Person person) {
        this.email = email;
        this.password = password;
        this.rolesList = rolesList;
        this.person = person;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public BranchOffice getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(BranchOffice branchOffice) {
        this.branchOffice = branchOffice;
    }
}
