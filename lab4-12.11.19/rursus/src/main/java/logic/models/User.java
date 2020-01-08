package logic.models;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Представляет сущность пользователя для бд.
 * @author Артемий Кульбако
 * @version 1.1
 */
@Data @Entity @Table(name = "users")
public class User implements Serializable {

    @Transient private static final long serialVersionUID = 4L;
    @Id private String email;
    @Column(nullable = false) private String password;
    @ElementCollection(fetch = FetchType.EAGER) @CollectionTable(name = "points") private List<Point> points;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.points = new ArrayList<>();
    }
}