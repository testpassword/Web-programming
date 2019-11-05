import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.Map;

@ManagedBean(name = "main")
@SessionScoped
public class MainBean implements Serializable {

    private static final long serialVersionUID = 4L;

    public void validate() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        Point point = new Point(facesContext.getExternalContext().getSessionId(true), Double.parseDouble(params.get("X-value")),
                Double.parseDouble(params.get("Y-value")), Double.parseDouble(params.get("R-value")));
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("db_con");
        EntityManager manager = managerFactory.createEntityManager();
        EntityTransaction transaction =  manager.getTransaction();
        transaction.begin();
        manager.persist(point);
        transaction.commit();
        manager.close();
        managerFactory.close();
    }
}