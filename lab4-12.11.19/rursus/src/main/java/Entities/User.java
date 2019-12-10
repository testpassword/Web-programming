package Entities;

import server.PasswordManager;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) private long id;
    @Transient private static final long serialVersionUID = 4L;
    @Column(nullable = false) private InternetAddress email;
    @Column(nullable = false) private String password;
    @ElementCollection(fetch = FetchType.EAGER) @CollectionTable(name = "points") private List<Point> points;

    public User() {}

    public User(String email, String password) throws AddressException {
        this.email = new InternetAddress(email);
        this.email.validate();
        this.password = PasswordManager.getHash(password, "SHA1");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public InternetAddress getEmail() {
        return email;
    }

    public void setEmail(InternetAddress email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}