package Page;

import java.util.ArrayList;
import Exception.*;
import Model.Course;

public class DropCourseConfirmationPage extends Page {

    private static String message = "These are the courses you are about to drop\n" +
            "Enter 'confirm' to drop the courses\n" +
            "Enter 'cancel' to cancel the operation\n";

    private int studentId;
    private ArrayList<Integer> dropCourseIds;

    public DropCourseConfirmationPage(int studentId, ArrayList<Integer> dropCourseIds) {
        super(message);
        this.studentId = studentId;
        this.dropCourseIds = dropCourseIds;
        showMessage();
        run();
    }

    public void run() {
        try {
            showDropCourses();
            String confirmation = getInput("Confirm or Cancel: ");
            switch (confirmation) {
                case "confirm":
                    Server.dropCourses(studentId, dropCourseIds);
                    showMessage("Courses dropped successfully\n");
                    new StudentHomePage(studentId);
                    break;
                case "cancel":
                    showMessage("Operation cancelled\n");
                    new ViewRegisteredCoursesPage(studentId);
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

    private void showDropCourses() {
        ArrayList<Course> dropCourses = Server.getCoursesByIds(dropCourseIds);
        showCourses(dropCourses);
    }

}
