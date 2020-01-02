package logic.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Представляет сущность точки из/в бд.
 */
@Data @Embeddable @Table(name = "points")
public class Point implements Serializable {

    @Transient private static final long serialVersionUID = 4L;
    private double x, y, r;
    private boolean coordsStatus;
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
        //TODO: условие для треугольника
        this.coordsStatus = (x <= 0 && y >= 0 && x >= -r && y <= r/2) || (x >= 0 && y >= 0 && y <= (x - r/2) * (-2)) ||
                (x >= 0 && y <= 0 && x * x + y * y <= Math.pow(r, 2));
    }

    @Override
    public String toString() {
        return "<tr><td>" + x + "</td>" +
                "<td>" + y + "</td>" +
                "<td>" + r + "</td>" +
                "<td style='color: " + ((coordsStatus) ? "green" : "red") + "'>" + coordsStatus + "</td>" +
                "<td>" + bornDate + "</td></tr>";
    }
}