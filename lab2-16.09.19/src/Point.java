import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Point {

    private double x, y, r;
    private boolean coordsStatus;
    private ZonedDateTime currentTime;

    Point(double x, double y, double r, String timezone) {
        this.x = x;
        this.y = y;
        this.r = r;
        coordsStatus = checkCoordinates(x, y, r);
        this.currentTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(timezone));
    }

    private boolean checkCoordinates(double x, double y, double r) {
        return  (((x >= -r/2) && (x <= 0) && (y >= 0) && (y <= r/2)) ||
                ((x >= 0) && (x <= r) && (y >= 0) && (y >= -r/2)) ||
                ((Math.pow(x, 2) + Math.pow(y, 2)) <= ((Math.pow(r, 2))/2) && (x >= 0) && (y >= 0)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0 && Double.compare(point.r, r) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, r);
    }

    @Override
    public String toString() {
        return "<tr><td>" + x + "</td>" +
                "<td>" + y + "</td>" +
                "<td>" + r + "</td>" +
                "<td>" + coordsStatus + "</td>" +
                "<td>" + currentTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")) + "</td></tr>";
    }
}