package Page;

import System.*;
import Exception.*;

public class increaseCapacityPage extends Page {

    private static final String message = """
            This is the course you are about to increase its capacity
            Enter 'back' to go back to the previous menu
            Enter 'cancel' to cancel the process
            Enter 'logout' to logout from the system
            """;

    private int courseId;
    private int departmentId;

    public increaseCapacityPage(int courseId, int departmentId) {
        super(message);
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
            showMessage("Unexpected error occurred\n" + e.getMessage());
            run();
        }
    }

    private int getCapacity() throws NavigationException, ValidationException {
        String input = getInput("Enter the new capacity: ");
        checkNavigation(input);
        Validation.validateCapacity(input);
        return Integer.parseInt(input);
    }
}
