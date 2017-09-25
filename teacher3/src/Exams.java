import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/exams")
public class Exams extends HttpServlet{
    public Exams(){}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            ResultSet resultSet = Main.getStatement().executeQuery("select id,name from exam;");
            out.println("<nav>\n" +
                    "        <ul>\n" +
                    "            <li><a href=\"/students\">Students</a></li>\n" +
                    "            <li><a href=\"/exams\">Exams</a></li>\n" +
                    "            <li><a href=\"/studentExams\">Students and Exams</a></li>\n" +
                    "        </ul>\n" +
                    "    </nav>");
            out.println("<table border=\"1\">");
            out.println("<thead>\n" +
                    "<tr>\n" +
                    "    <th>Exam's Name</th>\n" +
                    "  </tr>\n" +
                    "</thead>");
            while(resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getString("name") + "</td>");
                out.println("<td> <form action=\"/exams\" method=\"post\">\n" +
                        "  <button type=\"submit\" name=\"id\" value=\"" + resultSet.getInt("id") + "\" class=\"btn-link\">Delete</button>\n" +
                        "</form> </td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<a href=\"/exams/add\">Add exam</a>");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(out != null)
                out.close();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id=request.getParameter("id");
        response.setContentType("text/html");
        try {
            Main.getStatement().executeUpdate("DELETE from exam where id=" + id);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/exams");
    }

}
