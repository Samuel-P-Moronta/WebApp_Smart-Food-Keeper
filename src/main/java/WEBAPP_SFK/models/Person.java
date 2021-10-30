package WEBAPP_SFK.models;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "PERSON")
public class Person{
    @Id
    @Column(name = "IDENTIFICATION_CARD",length = 13)
    private String identificationCard;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "NAME")
    private String name;
    @Column(name = "SURNAME")
    private String surname;
    @Column(name = "LASTNAME")
    private String lastName;



    /*Empty constructor*/
    public Person(){
        /* Testing purpose */
    }


    public Person(String identificationCard, String email, String name, String surname, String lastName) {
        this.identificationCard = identificationCard;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.lastName = lastName;
    }

    public String getIdentificationCard() {
        return identificationCard;
    }

    public void setIdentificationCard(String identificationCard) {
        this.identificationCard = identificationCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
