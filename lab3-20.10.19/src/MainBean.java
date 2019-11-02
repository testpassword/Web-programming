import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.Map;

@ManagedBean(name = "main")
@SessionScoped
public class MainBean {

    private double x, y, r;

    public void test() {
        System.out.println("TEST SUCCESS");
        System.out.println("GETTING PARAMS");
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.x = Double.parseDouble(params.get("X-value"));
        this.y = Double.parseDouble(params.get("Y-value"));
        this.r = Double.parseDouble(params.get("R-value"));
        System.out.println("X = " + this.x + " Y = " + y + " R= " + r);
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
}
