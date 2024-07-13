package Page;

import java.sql.SQLException;
import java.util.ArrayList;
import Exception.*;
import Model.Course;
import System.Server;

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
        run();
    }

    public void run() {
        try {
            showDropCourses();
            String confirmation = getChoice("Confirm or Cancel: ");
            switch (confirmation) {
                case "confirm":
                    Server.dropCourses(studentId, dropCourseIds);
                    showMessage("Courses dropped successfully");
                    new StudentHomePage(studentId);
                    break;
                case "cancel":
                    showMessage("Operation cancelled");
                    new ViewRegisteredCoursesPage(studentId);
                    break;
                default:
                    invalidChoice();
                    run();
            }
        } catch (ValidationException e) {
            showMessage(e.getMessage());
            run();
        } catch (Exception e) {
            showMessage("Unexpected error occurred\n" + e.getMessage());
            run();
        }
    }

    private void showDropCourses() throws SQLException, DatabaseException {
        ArrayList<Course> dropCourses = Server.getCoursesByIds(dropCourseIds);
        Course.showCourses(dropCourses);
    }

}
