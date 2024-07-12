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
            """;

    private final int studentId;
    private ArrayList<Course> registeredCourses;

    public ViewRegisteredCoursesPage(int studentId) {
        super(message);
        this.studentId = studentId;
        showMessage();
        run();
    }

    public void run() {
        try {
            showRegisteredCourses();
            ArrayList<Integer> dropCourseIds = getDropCourseIds();
            new DropCourseConfirmationPage(studentId, dropCourseIds);
        } catch (NavigationBackException e) {
            new StudentHomePage(studentId);
        } catch (ValidationException e) {
            showMessage("\n" + e.getMessage() + "\n");
            run();
        } catch (Exception e) {
            showMessage("\nUnexpected error occurred\n" + e.getMessage() + "\n");
            run();
        }
    }

    public ArrayList<Integer> getDropCourseIds() throws NavigationBackException, ValidationException, SQLException, DataBaseException {
        String input = getInput("Enter course ids to drop: ");
        checkBack(input);
        String[] Ids = input.split(" ");
        ArrayList<Integer> dropCourseIds = Validation.getValidatedDropCourseIds(Ids, getRegisteredCourses());
        if(dropCourseIds.isEmpty()) {
            throw new ValidationException("Please select at least one valid course id to drop or enter 'back' to go back to the previous page\n");
        }
        return dropCourseIds;
    }

    private void showRegisteredCourses() throws SQLException, DataBaseException {
        ArrayList<Course> registeredCourses = getRegisteredCourses();
        showCourses(registeredCourses);
    }

    private ArrayList<Course> getRegisteredCourses() throws SQLException, DataBaseException {
        if(registeredCourses == null) {
            registeredCourses = Server.getRegisteredCourses(studentId);
        }
        return registeredCourses;
    }

}
