import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/studentExams")
public class StudentExams extends HttpServlet{
    public StudentExams(){}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            ResultSet resultSet = Main.getStatement().executeQuery("select student,exam,student.name as student,exam.name as exam, points from student,exam,tasks where tasks.student=student.id and tasks.exam=exam.id;");
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
                    "    <th>Student</th>\n" +
                    "    <th>Exam</th>\n" +
                    "    <th>Points</th>\n" +
                    "  </tr>\n" +
                    "</thead>");
            while(resultSet.next()) {
                out.println("<tr>");
                out.println("<td>" + resultSet.getString("student") + "</td>");
                out.println("<td>" + resultSet.getString("exam")+ "</td>");
                out.println("<td>" + resultSet.getInt("points")+ "</td>");
                out.println("<td> <form action=\"/studentExams?student=" + resultSet.getInt("student") + "\" method=\"post\">\n" +
                        "  <button type=\"submit\" name=\"exam\" value=\"" + resultSet.getInt("exam") + "\" class=\"btn-link\">Delete</button>\n" +
                        "</form> </td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<a href=\"/studentExams/add\">Add finished task</a>");
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

        String exam=request.getParameter("exam");
        String student=request.getParameter("student");
        response.setContentType("text/html");
        try {
            Main.getStatement().executeUpdate("DELETE from tasks where student=" + student + " and exam=" + exam);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/studentExams");
    }
}
