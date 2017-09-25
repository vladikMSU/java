/**
 * Created by vladislav on 16.09.17.
 */

import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final int N = 20;

    private static final String url = "jdbc:mysql://localhost:3306/students";
    private static final String user = "root";
    private static final String password = "Dkflbckfd_04";

    private static Scanner in;

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    private static String[] commands = {
            "get_help",
            "add_student",
            "print_all_students",
            "assign_exam_for",
            "get_info_about",
            "print_credited_students_list",
            "exam_list",
            "exit"
    };
    private static String[] descriptions = {
            " - print the list of all commands.",
            " <student_name> - add a student called <student_name>.",
            " - print the list of all students.",
            " <student_name> <exam_name> <points> - mark that <student_name> passes <exam_name> with <points> points",
            " <student_name> - print the information about a student called <student_name>.",
            " - print the list of all credited students.",
            " - print the list of all exams.",
            " - stop the program."
    };


    public static void main(String[] args) {
        try {
            connection = DriverManager.getConnection(url, user, password);

            statement = connection.createStatement();

            System.out.println("Greetings, teacher! This is your helping programm!");
            System.out.println("Use \"get_help\" command to get the list of all commands.");

            in = new Scanner(System.in);
            String command = "";
            while(!(command=in.next()).matches(commands[7])) {
                if (command.matches(commands[0]))
                    printHelp();
                else if (command.matches(commands[1]))
                    addStudent(in.next());
                else if (command.matches(commands[2]))
                    printAll();
                else if (command.matches(commands[3]))
                    assignExam(in.next(), in.next(), in.nextInt());
                else if (command.matches(commands[4]))
                    printInfoAbout(in.next());
                else if (command.matches(commands[5]))
                    printListOfCredited();
                else if (command.matches(commands[6]))
                    printExams();
                else {
                    System.out.println("Unknown command \"" + command + "\"!");
                    System.out.println("Use \"get_help\" to see the list of all commands!");
                }

                System.out.println("\nWhat would you like to do now: ");
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,statement and resultset here
            try { connection.close(); } catch(SQLException se) {se.printStackTrace(); }
            try { statement.close(); } catch(SQLException se) { se.printStackTrace();}
            try { resultSet.close(); } catch(SQLException se) { se.printStackTrace();}
        }
    }

    private static void printHelp() {
        System.out.println("You can use the following commands:");
        for (int i=0; i < commands.length; ++i)
            System.out.println("  " + commands[i] + descriptions[i]);
    }

    private static void addStudent(String studentName) throws SQLException {
        statement.executeUpdate("INSERT INTO students.student (name) VALUES ('" + studentName + "');");
        System.out.println("Student " + studentName + " has been successfully added!");
    }

    private static void printAll() throws SQLException {
        resultSet = statement.executeQuery("select name from student;");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }
    }

    private static void printInfoAbout(String student) throws SQLException {
        resultSet = statement.executeQuery("select id from student where name='" + student + "';");
        resultSet.next();
        int studentId=resultSet.getInt("id");
        resultSet = statement.executeQuery("select sum(points) from tasks where student=" + studentId);
        resultSet.next();
        int points=resultSet.getInt(1);
        System.out.println("Student name is " + student + ", this student has " + points + " points. Exams are "
                + (points>=N?"passed.":"not passed."));
    }

    private static void assignExam(String student, String exam, int points) throws SQLException {
        resultSet = statement.executeQuery("select id from student where name='" + student + "';");
        resultSet.next();
        int studentId=resultSet.getInt("id");

        resultSet = statement.executeQuery("select id from exam where name='" + exam + "';");
        resultSet.next();
        int examId=resultSet.getInt("id");

        resultSet = statement.executeQuery("select COUNT(*) as count from tasks where exam=" + examId +
                " and student=" + studentId + ";");
        resultSet.next();
        if (resultSet.getInt("count") != 0) {
            System.out.println("This student has already passed this exam!");
            System.out.println("Do you want to update his score? Answer 'YES' or 'NO':");
            if (in.next().matches("YES")) {
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

    private static void printListOfCredited() throws SQLException {
        resultSet = statement.executeQuery("select name from student " +
                "inner join tasks on student.id = tasks.student where tasks.student=student.id " +
                "group by name having sum(tasks.points)>" + N);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("name"));
        }
    }
}