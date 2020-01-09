package logic.models;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Представляет сущность точки для бд.
 * @author Артемий Кульбако
 * @version 1.2
 */
@Data @Embeddable @Table(name = "points")
public class Point implements Serializable {

    @Transient private static final long serialVersionUID = 4L;
    @Column(nullable = false) private double x, y, r;
    @Column(nullable = false) private boolean status;
    private Date bornDate;

    public Point(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        checkCoordinates();
        bornDate = new Date();
    }

    public Point() {}

    private void checkCoordinates() {
        this.status = (x <= 0 && y >= 0 && x >= -r && y <= r/2) || ((y >= -x - r) && x <= 0 && y <= 0) ||
                (x >= 0 && y <= 0 && x * x + y * y <= Math.pow(r, 2));
    }

    @Deprecated
    public String toHtmlMarkup() {
        return "<tr><td>" + x + "</td>" +
                "<td>" + y + "</td>" +
                "<td>" + r + "</td>" +
                "<td style='color: " + ((status) ? "green" : "red") + "'>" + status + "</td>" +
                "<td>" + bornDate + "</td></tr>";
    }
}