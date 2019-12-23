package entities;

import logic.PasswordManager;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Transient private static final long serialVersionUID = 4L;
    @Id @Column(nullable = false) private String email; //так-как пользователи не могут менять почту, то можно использовать как Id
    @Column(nullable = false) private String password;
    @ElementCollection(fetch = FetchType.LAZY) @CollectionTable(name = "points") private List<Point> points;

    public User() {}

    public User(String email, String password) {
        this.email = PasswordManager.getHash(email, "MD5");
        this.password = PasswordManager.getHash(password, "SHA1");
        points = new ArrayList<>();
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