package entities;

import server.PasswordManager;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Transient private static final long serialVersionUID = 4L;
    @Id @Column(nullable = false) private InternetAddress email; //так-как пользователи не могут менять почту, то можно использовать как Id
    @Column(nullable = false) private String password;
    @ElementCollection(fetch = FetchType.EAGER) @CollectionTable(name = "points") private List<Point> points;

    public User() {}

    public User(String email, String password) throws AddressException {
        this.email = new InternetAddress(email);
        this.email.validate();
        this.password = PasswordManager.getHash(password, "SHA1");
        points = new ArrayList<>();
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

    @Override
    public String toString() {
        return "User{" +
                "email=" + email +
                ", password='" + password + '\'' +
                ", points=" + points +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}