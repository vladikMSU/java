import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/studentExams/add")
public class StudentExamsAdd extends HttpServlet{
    public StudentExamsAdd(){}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>\n" +
                    "   <body>\n" +
                    "      <form action = \"add\" method = \"POST\">\n" +
                    "         Student's name: <input type = \"text\" name = \"student\">\n" +
                    "<br />\n" +
                    "         Exam's name: <input type = \"text\" name = \"exam\">\n" +
                    "<br />\n" +
                    "         Points: <input type = \"text\" name = \"points\">\n" +
                    "<br />\n" +
                    "       <input type = \"submit\" value = \"Submit\" />" +
                    "      </form>\n" +
                    "   </body>\n" +
                    "</html>");
        } finally {
            if(out != null)
                out.close();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String student=request.getParameter("student");
        String exam=request.getParameter("exam");
        String points=request.getParameter("points");
        response.setContentType("text/html");
        try {
            Statement statement=Main.getStatement();
            ResultSet resultSet = statement.executeQuery("select id from student where name='" + student + "';");
            resultSet.next();
            int studentId=resultSet.getInt("id");
            resultSet = statement.executeQuery("select id from exam where name='" + exam + "';");
            resultSet.next();
            int examId=resultSet.getInt("id");
            Main.getStatement().executeUpdate("INSERT INTO tasks (student,exam,points) VALUES ('" + studentId + "','" + examId + "','" + points + "');");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/studentExams");
    }

}
