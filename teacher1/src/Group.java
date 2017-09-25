/**
 * Created by vladislav on 15.03.17.
 */

import java.util.*;

class Group {
    final int neededNumberOfTasks = 5;
    private Map<String, Student> students;

    Group() {
        students = new TreeMap<>();
    }

    void addStudent(Student student) {
        students.put(student.getName(), student);
    }

    List<Student> getListOfStudents() {
        return new ArrayList<>(students.values());
    }

    void assignTaskFor(String studentName) {
        Student student = students.get(studentName);
        if (student == null) {
            System.out.println("No such student!");
        } else {
            student.assignTask();
        }
    }

    void printInfoAboutStudent(String studentName) {
        Student student = students.get(studentName);
        if (student == null) {
            System.out.println("No such student!");
        } else {
            System.out.print("Number of tasks completed by " + studentName);
            System.out.println(": " + student.getNumberOfTasksComplete());
        }
    }
}
