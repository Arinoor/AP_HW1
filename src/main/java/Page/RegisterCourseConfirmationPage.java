package Page;

import java.util.ArrayList;
import Exception.*;
import Model.Course;

public class RegisterCourseConfirmationPage extends Page {

    private static String message = "These are the courses you are about to register\n" +
            "Enter 'confirm' to register the courses\n" +
            "Enter 'cancel' to cancel the operation\n";

    private int studentId;
    private int departmentId;
    private ArrayList<Integer> registerCourseIds;

    public RegisterCourseConfirmationPage(int studentId, int departmentId, ArrayList<Integer> registerCourseIds) {
        super(message);
        this.studentId = studentId;
        this.departmentId = departmentId;
        this.registerCourseIds = registerCourseIds;
        showMessage();
        run();
    }

    public void run() {
        try {
            showRegisterCourses();
            String confirmation = getInput("Confirm or Cancel: ");
            switch (confirmation) {
                case "confirm":
                    Server.registerCourses(studentId, registerCourseIds);
                    showMessage("Courses registered successfully\n");
                    new StudentHomePage(studentId);
                    break;
                case "cancel":
                    showMessage("Operation cancelled\n");
                    new ViewAvailableCoursesPage(studentId, departmentId);
                    break;
                default:
                    invalidChoice();
                    run();
            }
        } catch (Exception e) {
            showMessage(e.getMessage() + "\n");
            run();
        }
    }

    private void showRegisterCourses() {
        ArrayList<Course> registerCourses = Server.getCoursesByIds(registerCourseIds);
        showCourses(registerCourses);
    }
}
