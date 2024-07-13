package Page;

import System.*;
import Exception.*;

public class IncreaseCapacityPage extends Page {

    private static final String message = """
            Enter extra capacity to increase
            Enter 'back' to go back to the previous menu
            Enter 'cancel' to cancel the process
            Enter 'logout' to logout from the system
            """;

    private int courseId;
    private int departmentId;

    public IncreaseCapacityPage(int courseId, int departmentId) {
        super(message);
        this.courseId = courseId;
        this.departmentId = departmentId;
        run();
    }

    public void run() {
        try {
            int capacity = getCapacity();
            Server.increaseCapacity(courseId, capacity);
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

    private int getCapacity() throws NavigationException, ValidationException {
        String input = getInput("Enter the extra capacity: ");
        checkNavigation(input);
        Validation.validateCapacity(input);
        return Integer.parseInt(input);
    }
}
