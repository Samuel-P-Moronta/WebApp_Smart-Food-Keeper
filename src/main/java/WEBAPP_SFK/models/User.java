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
    @Column(unique = true)
    private String username;
    private String password;
    // Carga en linea
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<RoleApp> rolesList;
    @OneToMany(mappedBy = "user")
    private Collection<Notification> notifications;
    // Hacemos un Join con el Id de la persona
    @JoinColumn(name = "ID_PERSON")
    // Se debe de indicar el atributo mappedBy dado que no es
    // La clase duena de la relacion
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Person person;
    @ManyToOne
    // Muchos usuarios pueden pertenecer a una misma empresa
    private Organization organization;


    public User() {
    }

    public User(String email, String username, String password, Set<RoleApp> rolesList, Person person, Organization organization) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.rolesList = rolesList;
        this.person = person;
        this.organization = organization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
