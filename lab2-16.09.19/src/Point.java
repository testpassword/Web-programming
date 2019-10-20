import java.io.Serializable;
import java.util.Date;

public class Point implements Serializable {

    private double x, y, r;
    private boolean coordsStatus;
    public static final long serialVersionUID = 4L;

    public Point(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
        coordsStatus = checkCoordinates(x, y, r);
    }

    private boolean checkCoordinates(double x, double y, double r) {
        return  (((x >= -r/2) && (x <= 0) && (y >= 0) && (y <= r/2)) && (y <= x + r/2) ||
                ((x >= 0) && (x <= r) && (y <= 0) && (y >= -r/2)) ||
                ((Math.pow(x, 2) + Math.pow(y, 2)) <= ((Math.pow(r, 2))) && (x >= 0) && (y >= 0)));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean coordsStatus() {
        return coordsStatus;
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
    public String toString() {
        return "<tr><td>" + x + "</td>" +
                "<td>" + y + "</td>" +
                "<td>" + r + "</td>" +
                "<td style='color: " + ((coordsStatus) ? "green" : "red") + "'>" + coordsStatus + "</td>" +
                "<td>" + new Date().toString() + "</td></tr>";
    }
}