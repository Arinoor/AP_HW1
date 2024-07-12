package Page;

import java.sql.SQLException;
import java.util.ArrayList;
import Exception.*;
import Model.Course;
import System.Server;

public class RegisterCourseConfirmationPage extends Page {

    private static final String message = """
            These are the courses you are about to register
            Enter 'confirm' to register the courses
            Enter 'cancel' to cancel the operation
            """;

    private final int studentId;
    private final int departmentId;
    private final ArrayList<Integer> registerCourseIds;

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
        } catch (ValidationException e) {
            showMessage("\n" + e.getMessage() + "\n");
            run();
        } catch (Exception e) {
            showMessage("\nUnexpected error occurred\n" + e.getMessage() + "\n");
            run();
        }
    }

    private void showRegisterCourses() throws SQLException, DataBaseException {
        ArrayList<Course> registerCourses = Server.getCoursesByIds(registerCourseIds);
        showCourses(registerCourses);
    }
}
