import javax.faces.context.FacesContext;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RealHumanBean implements Serializable {

    private static final long serialVersionUID = 4L;
    private EntityManagerFactory managerFactory;
    private EntityManager manager;
    private EntityTransaction transaction;
    private List<Point> points;

    public void validate() {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            managerFactory = Persistence.createEntityManagerFactory("db_con");
            manager = managerFactory.createEntityManager();
            transaction = manager.getTransaction();
            Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
            Point point = new Point(facesContext.getExternalContext().getSessionId(true), Double.parseDouble(params.get("X-value")),
                    Double.parseDouble(params.get("Y-value")), Double.parseDouble(params.get("R-value")));
            addPointToDB(point);
            points = getAllEntitiesFromDB(facesContext.getExternalContext().getSessionId(true));
        } finally {
            manager.close();
            managerFactory.close();
        }
    }

    private void addPointToDB(Point addedPoint) {
        transaction.begin();
        manager.persist(addedPoint);
        transaction.commit();
    }

    private List<Point> getAllEntitiesFromDB(String sessionID) {
        transaction.begin();
        TypedQuery<Point> query = manager.createQuery("SELECT p FROM Point p WHERE p.owner = :owner", Point.class);
        return query.setParameter("owner", sessionID).getResultList();
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealHumanBean)) return false;
        RealHumanBean that = (RealHumanBean) o;
        return Objects.equals(managerFactory, that.managerFactory) &&
                Objects.equals(manager, that.manager) &&
                Objects.equals(transaction, that.transaction) &&
                Objects.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(managerFactory, manager, transaction, points);
    }

    @Override
    public String toString() {
        return "RealHumanBean{" +
                "managerFactory=" + managerFactory +
                ", manager=" + manager +
                ", transaction=" + transaction +
                ", points=" + points +
                '}';
    }
}