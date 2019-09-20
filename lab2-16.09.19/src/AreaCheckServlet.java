import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.ws.http.HTTPException;
import java.util.*;
import java.io.*;

public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List tableRows = (List) session.getAttribute("tableRows");
        if (tableRows == null) {
            tableRows = new ArrayList<String>();
            session.setAttribute("tableRows", tableRows);
            tableRows.add("<table id='outputTable'><tr>" +
                    "<th>x</th>" +
                    "<th>y</th>" +
                    "<th>r</th>" +
                    "<th>Точка входит в ОДЗ</th>" +
                    "<th>Текущее время</th></tr>");
        }
        double x = Double.parseDouble(req.getParameter("x"));
        double y = Double.parseDouble(req.getParameter("y"));
        double r = Double.parseDouble(req.getParameter("r"));
        try (PrintWriter writer = resp.getWriter()) {
            if (checkData(x, y, r)) {
                tableRows.add(new Point(x, y, r, req.getParameter("timezone")).toString());
                tableRows.forEach(writer::println);
            } else throw new HTTPException(400);
        }
    }

    private boolean checkData(double x, double y, double r) {
        Double[] xInterval = {-2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5, 2.0};
        Double[] rInterval = {1.0, 2.0, 3.0, 4.0, 5.0};
        return (Arrays.asList(xInterval).contains(x) && (y > -5 && y < 3) && Arrays.asList(rInterval).contains(r));
    }
}

//TODO ajax с json
//TODO починить кодировку