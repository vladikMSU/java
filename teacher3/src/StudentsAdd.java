import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/students/add")
public class StudentsAdd extends HttpServlet{
    public StudentsAdd(){}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>\n" +
                    "   <body>\n" +
                    "      <form action = \"add\" method = \"POST\">\n" +
                    "         Name: <input type = \"text\" name = \"name\">\n" +
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

        String name=request.getParameter("name");
        response.setContentType("text/html");
        try {
            Main.getStatement().executeUpdate("INSERT INTO student (name) VALUES ('" + name + "');");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/students");
    }

}
