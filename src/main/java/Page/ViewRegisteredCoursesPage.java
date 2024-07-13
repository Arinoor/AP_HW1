package Page;

import Model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import Exception.*;
import System.*;

public class ViewRegisteredCoursesPage extends Page {

    private static final String message = """
            There are your registered courses
            You can drop one or more courses by entering their id separated by space
            Enter 'back' to go back to the previous page
            Enter 'logout' to logout from the system
            """;

    private final int studentId;
    private ArrayList<Course> registeredCourses;

    public ViewRegisteredCoursesPage(int studentId) {
        super(message);
        this.studentId = studentId;
        run();
    }

    public void run() {
        try {
            showRegisteredCourses();
            ArrayList<Integer> dropCourseIds = getDropCourseIds();
            new DropCourseConfirmationPage(studentId, dropCourseIds);
        } catch (NavigationBackException e) {
            new StudentHomePage(studentId);
        } catch (NavigationLogoutException e) {
            new HomePage();
        } catch (ValidationException e) {
            showMessage(e.getMessage());
            run();
        } catch (Exception e) {
            showMessage("Unexpected error occurred\n" + e.getMessage());
            run();
        }
    }

    public ArrayList<Integer> getDropCourseIds() throws NavigationException, ValidationException, SQLException, DatabaseException {
        String input = getInput("Enter course ids to drop: ");
        checkBack(input);
        checkLogout(input);
        String[] Ids = input.split(" ");
        ArrayList<Integer> dropCourseIds = Validation.getValidatedCourseIds(Ids, getRegisteredCourses(), "is not registered");
        if(dropCourseIds.isEmpty()) {
            throw new ValidationException("Please select at least one valid course id to drop or enter 'back' to go back to the previous page\n");
        }
        return dropCourseIds;
    }

    private void showRegisteredCourses() throws SQLException, DatabaseException {
        ArrayList<Course> registeredCourses = getRegisteredCourses();
        if(registeredCourses.isEmpty()) {
            showMessage("You have not registered any course yet");
        }
        else {
            Course.showCourses(registeredCourses);
        }
    }

    private ArrayList<Course> getRegisteredCourses() throws SQLException, DatabaseException {
        if(registeredCourses == null) {
            registeredCourses = Server.getRegisteredCourses(studentId);
        }
        return registeredCourses;
    }

}
