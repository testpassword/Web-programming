package models;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Transient private static final long serialVersionUID = 4L;
    @Id @Column(nullable = false) private String email; //так-как пользователи не могут менять почту, то можно использовать как Id
    @Column(nullable = false) private String password;
    @ElementCollection(fetch = FetchType.LAZY) @CollectionTable(name = "points") private List<Point> points;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        points = new ArrayList<>();
    }
}