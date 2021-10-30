package WEBAPP_SFK.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Embeddable
public class User implements Serializable {
    @Id
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    private RoleApp userRole;
    @JoinColumn(name = "ID_PERSON")
    @OneToOne
    private Person person;

    /* Empty constructor */
    public User(){
        /* Testing purpose */
    }
    public User(String email, String username, String password, RoleApp userRole) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.person = person;
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

    public RoleApp getUserRole() {
        return userRole;
    }

    public void setUserRole(RoleApp userRole) {
        this.userRole = userRole;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
