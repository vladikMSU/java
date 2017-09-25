import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.util.Scanner;

public class Main {
    static final int N = 10;
    private static final String url = "jdbc:mysql://localhost:3306/student";
    private static final String user = "root";
    private static final String password = "Dkflbckfd_04";
    private static Scanner scan;
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    static Statement getStatement() throws ClassNotFoundException, SQLException {
        if (statement==null) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        }
        return statement;
    }

    private static void printInfo(String student) throws SQLException {
        resultSet = statement.executeQuery("select id from student where name='" + student + "';");
        resultSet.next();
        int studentId=resultSet.getInt("id");
        resultSet = statement.executeQuery("select sum(points) from tasks where student=" + studentId);
        resultSet.next();
        int points=resultSet.getInt(1);
        System.out.println("Student name is " + student + ", this student has " + points + " points. Exams are "
                + (points>=N?"passed.":"not passed."));
    }

    private static void passed(String student, String exam, int points) throws SQLException {
        resultSet = statement.executeQuery("select id from student where name='" + student + "';");
        resultSet.next();
        int studentId=resultSet.getInt("id");
        resultSet = statement.executeQuery("select id from exam where name='" + exam + "';");
        resultSet.next();
        int examId=resultSet.getInt("id");
        resultSet = statement.executeQuery("select COUNT(*) as count from tasks where exam=" + examId + " and student=" + studentId
        + ";");
        resultSet.next();
        if (resultSet.getInt("count")!=0) {
            System.out.println("This student already passed this exam, do you want to update his score? Answer 'YES' or 'NO':");
            if (scan.next().matches("YES")) {
                System.out.println("UPDATE tasks SET points=" + points + " where student=" + studentId +
                        " and exam=" + examId + ";");
                statement.executeUpdate("UPDATE tasks SET points=" + points + " where student=" + studentId +
                        " and exam=" + examId + ";");
            }
            return;
        }
        statement.executeUpdate("INSERT INTO students.tasks (student,exam,points) VALUES (" + studentId
                + "," + examId + "," + points + ");");
    }

    private static void printExams() throws SQLException {
        resultSet = statement.executeQuery("select name from exam;");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }
    }

    private static void add(String student) throws SQLException {
        statement.executeUpdate("INSERT INTO students.student (name) VALUES ('" + student + "');");
    }
}
