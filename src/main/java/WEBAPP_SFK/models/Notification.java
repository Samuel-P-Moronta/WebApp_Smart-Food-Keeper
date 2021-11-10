package WEBAPP_SFK.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTIFICATION")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TITLE")
    private String title;
    @CreationTimestamp
    private LocalDateTime sendDate;
    @Column(name = "DESCRIPTION")
    private String description;
    @JsonIgnore
    // Carga Perezosa en JPA
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    public Notification() {
    }

    public Notification(String title, String description, User user) {
        this.title = title;
        this.sendDate = sendDate;
        this.description = description;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUserCredential() {
        return user;
    }

    public void setUserCredential(User user) {
        this.user = user;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
