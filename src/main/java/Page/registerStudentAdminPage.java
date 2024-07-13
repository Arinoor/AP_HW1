package Page;

import Exception.*;
import System.*;

import java.util.ArrayList;

public class registerStudentAdminPage extends Page {

    private static final String message = """
            Enter student id to register
            Enter 'back' to go back to the previous menu
            Enter 'cancel' to cancel the process
            Enter 'logout' to logout from the system
            """;

    private int courseId;
    private int departmentId;

    public registerStudentAdminPage(int courseId, int departmentId) {
        super(message);
        this.courseId = courseId;
        this.departmentId = departmentId;
        run();
    }

    public void run() {
        try {
            int studentId = getStudentId();
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(studentId);
            Server.registerCourses(courseId, temp);
            showMessage("Student registered successfully");
            new ViewCourseAdminPage(courseId, departmentId);
        } catch (NavigationBackException | NavigationCancelException e) {
            new ViewCourseAdminPage(courseId, departmentId);
        } catch (ValidationException e) {
            showMessage(e.getMessage());
            run();
        } catch (Exception e) {
            showExceptionMessage(e);
            run();
        }
    }

    private int getStudentId() throws NavigationException, ValidationException {
        String input = getInput("Enter student id: ");
        checkNavigation(input);
        Validation.validateStudentId(input);
        return Integer.parseInt(input);
    }

}
