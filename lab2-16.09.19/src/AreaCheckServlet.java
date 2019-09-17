import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.*;
import java.io.*;

@WebServlet(name = "AreaCheck", urlPatterns = {"/checker"})
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List tableRows = (List) session.getAttribute("tableRows");
        if (tableRows == null) session.setAttribute("tableRows", new ArrayList<String>());
        double x = Double.parseDouble(req.getParameter("x"));
        double y = Double.parseDouble(req.getParameter("y"));
        double r = Double.parseDouble(req.getParameter("r"));
        String timezone = req.getParameter("timezone");
        //установить таймзону
        if (checkData(x, y, r)) {
            String coordsStatus = checkCoordinates(x, y, r);
            /*
            текущее время
            время работы скрипта
            */
            tableRows.add("<table id='outputTable'><tr>" +
                    "<th>x</th>" +
                    "<th>y</th>" +
                    "<th>r</th>" +
                    "<th>Точка входит в ОДЗ</th>" +
                    "<th>Текущее время</th>" +
                    "<th>Время работы скрипта</th></tr>");
            tableRows.add("<tr><td>" + x + "</td>" +
                    "<td>" + y + "</td>" +
                    "<td>" + r + "</td>" +
                    "<td>" + coordsStatus + "</td>" +
                    /*"<td>" + currentTime + "</td>" +
                    "<td>" + benchamarkTime +*/ "</td></tr>");
            try (PrintWriter writer = resp.getWriter()) {
                tableRows.forEach(writer::println);
            }
        } else {
            //вернуть http-код 400
        }
    }

    private boolean checkData(double x, double y, double r) {
        double[] xInterval = {-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2};
        double[] rInterval = {1, 2, 3, 4, 5};
        return (Arrays.asList(xInterval).contains(x) && (y > -5 && y < 3) && Arrays.asList(rInterval).contains(r));
    }

    private String checkCoordinates(double x, double y, double r) {
        if (((x >= -r/2) && (x <= 0) && (y >= 0) && (y <= r/2)) ||
                ((x >= 0) && (x <= r) && (y >= 0) && (y >= -r/2)) ||
                ((Math.pow(x, 2) + Math.pow(y, 2)) <= ((Math.pow(r, 2))/2) && (x >= 0) && (y >= 0))) return "да";
        else return "нет";
    }
}