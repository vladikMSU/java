/**
 * Created by vladislav on 15.03.17.
 */

class Student {
    private String name;
    private Integer numberOfTasksComplete;

    Student(String name_val) {
        name = name_val;
        numberOfTasksComplete = 0;
    }

    String getName() {
        return name;
    }

    void assignTask() {
        ++numberOfTasksComplete;
    }

    Integer getNumberOfTasksComplete() {
        return numberOfTasksComplete;
    }
}
