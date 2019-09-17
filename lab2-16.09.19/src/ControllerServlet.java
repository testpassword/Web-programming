import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import javax.servlet.http.*;

@WebServlet(name = "Controller", urlPatterns = {"/app"})
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("x") == null || req.getParameter("y") == null ||
                req.getParameter("r") == null || req.getParameter("timezone") == null)
            req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        else getServletContext().getRequestDispatcher("/checker").forward(req, resp);
    }
}