package server;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "points")
public class Point implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;
    @Transient private static final long serialVersionUID = 4L;
    private String owner;
    private double x, y, r;
    private boolean coordsStatus;
    private Date bornDate;

    public Point(String owner, double x, double y, double r) {
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.r = r;
        coordsStatus = checkCoordinates(x, y, r);
        bornDate = new Date();
    }

    public Point() {}

    private boolean checkCoordinates(double x, double y, double r) {
        return (x <= 0 && y >= 0 && x >= -r && y <= r/2) || (x >= 0 && y >= 0 && y <= (x - r/2) * (-2)) ||
                (x >= 0 && y <= 0 && x * x + y * y <= Math.pow(r, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0 &&
                Double.compare(point.r, r) == 0 &&
                coordsStatus == point.coordsStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, owner, x, y, r, coordsStatus, bornDate);
    }

    @Override
    public String toString() {
        return "<tr><td>" + x + "</td>" +
                "<td>" + y + "</td>" +
                "<td>" + r + "</td>" +
                "<td style='color: " + ((coordsStatus) ? "green" : "red") + "'>" + coordsStatus + "</td>" +
                "<td>" + bornDate + "</td></tr>";
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isCoordsStatus() {
        return coordsStatus;
    }

    public void setCoordsStatus(boolean coordsStatus) {
        this.coordsStatus = coordsStatus;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }
}