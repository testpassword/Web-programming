import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
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
        String timezone = req.getParameter("timezone");
        try (PrintWriter writer = resp.getWriter()) {
            if (checkData(x, y, r)) {
                String coordsStatus = checkCoordinates(x, y, r);
                ZonedDateTime currentTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of(timezone));
                tableRows.add("<tr><td>" + x + "</td>" +
                        "<td>" + y + "</td>" +
                        "<td>" + r + "</td>" +
                        "<td>" + coordsStatus + "</td>" +
                        "<td>" + currentTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss")) + "</td></tr>");
                tableRows.forEach(writer::println);
            } else writer.println("400 - СЕРВЕР ПОЛУЧИЛ НЕКОРРЕКТНЫЕ ДАННЫЕ");
        }
    }

    private boolean checkData(double x, double y, double r) {
        Double[] xInterval = {-2.0, -1.5, -1.0, -0.5, 0.0, 0.5, 1.0, 1.5, 2.0};
        Double[] rInterval = {1.0, 2.0, 3.0, 4.0, 5.0};
        return (Arrays.asList(xInterval).contains(x) && (y > -5 && y < 3) && Arrays.asList(rInterval).contains(r));
    }

    private String checkCoordinates(double x, double y, double r) {
        if (((x >= -r/2) && (x <= 0) && (y >= 0) && (y <= r/2)) ||
                ((x >= 0) && (x <= r) && (y >= 0) && (y >= -r/2)) ||
                ((Math.pow(x, 2) + Math.pow(y, 2)) <= ((Math.pow(r, 2))/2) && (x >= 0) && (y >= 0))) return "да";
        else return "нет";
    }
}

//TODO ajax с json
//TODO перехват ServletException
//TODO починить кодировку
//TODO страница для ошибок клиента