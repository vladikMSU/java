/**
 * Created by vladislav on 15.03.17.
 */

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Group group = new Group();

    private static String[] commands = {
            "get_help",
            "add_student",
            "print_all_students",
            "assign_task_for",
            "get_info_about",
            "print_credited_students_list",
            "exit"
    };
    private static String[] descriptions = {
            " - print the list of all commands",
            " <student_name> - add a student called <student_name>.",
            " - print the list of all students.",
            " <student_name> - assign a task for a student called <student_name>.",
            " <student_name> - print the information about a student called <student_name>",
            " - print the list of all credited students.",
            " - stop the program."
    };

    public static void main(String[] args) {
        System.out.println("Greetings, teacher! This is your helping programm!");
        System.out.println("Use \"get_help\" command to get the list of all commands.");

        Scanner in = new Scanner(System.in);

        String command = "";
        while (!(command=in.next()).matches(commands[6])) {
            if (command.matches(commands[0]))
                printHelp();
            else if (command.matches(commands[1]))
                addStudent(in.next());
            else if (command.matches(commands[2]))
                printAll();
            else if (command.matches(commands[3]))
                assignTaskFor(in.next());
            else if (command.matches(commands[4]))
                printInfoAbout(in.next());
            else if (command.matches(commands[5]))
                printListOfCredited();
            else {
                System.out.println("Unknown command \"" + command + "\"!");
                System.out.println("Use \"get_help\" to see the list of all commands!");
            }

            System.out.println("\nWhat would you like to do now: ");
        }
    }

    private static void printHelp() {
        for (int i=0; i<commands.length; ++i)
            System.out.println(commands[i] + descriptions[i]);
    }

    private static void addStudent(String studentName) {
        group.addStudent(new Student(studentName));
        System.out.println("Student " + studentName + " has been successfully added!");
    }

    private static void printAll() {
        List<Student> listOfStudents = group.getListOfStudents();
        for (Student student : listOfStudents)
            System.out.println(student.getName());
    }

    private static void assignTaskFor(String studentName) {
        group.assignTaskFor(studentName);
        System.out.println("A task has been successfully assigned for student " + studentName + "!");
    }

    private static void printInfoAbout(String studentName) {
        group.printInfoAboutStudent(studentName);
    }

    private static void printListOfCredited() {
        List<Student> listOfStudents = group.getListOfStudents();
        if (listOfStudents.isEmpty()) {
            System.out.println("The list of students is empty! Add some students to perform this command!");
        } else {
            int numOfCreditedStudents = 0;

            for (Student student : listOfStudents)
                if (student.getNumberOfTasksComplete() >= group.neededNumberOfTasks) {
                    System.out.println(student.getName());
                    ++numOfCreditedStudents;
                }

            if (numOfCreditedStudents == 0)
                System.out.println("There are no credited students!");
        }
    }
}
