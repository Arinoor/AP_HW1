package Page;

import Exception.*;
import Model.Course;
import System.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewAvailableCoursesPage extends Page {

    private String message;
    private final int studentId;
    private final int departmentId;
    private ArrayList<Course> availableCourses;

    public ViewAvailableCoursesPage(int studentId, int departmentId) throws SQLException, DatabaseException {
        super();
        setMessage();
        showMessage();
        this.studentId = studentId;
        this.departmentId = departmentId;
        run();
    }

    public void run() {
        try {
            showAvailableCourses();
            ArrayList<Integer> registerCourseIds = getRegisterCourseIds();
            new RegisterCourseConfirmationPage(studentId, departmentId, registerCourseIds);
        } catch (NavigationBackException e) {
            new ChooseDepartmentToViewAvailableCoursesPage(studentId);
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

    private void showAvailableCourses() throws SQLException, DatabaseException {
        ArrayList<Course> availableCourses = getAvailableCourses();
        if(availableCourses.isEmpty()) {
            showMessage("No available courses to register");
        }
        else {
            Course.showCourses(availableCourses);
        }
    }

    private ArrayList<Integer> getRegisterCourseIds() throws NavigationException, ValidationException {
        String input = getInput("Enter course ids to register: ");
        checkBack(input);
        checkLogout(input);
        String[] Ids = input.split(" ");
        ArrayList<Integer> registerCourseIds = Validation.getValidatedCourseIds(Ids, availableCourses, "is not available");
        if(registerCourseIds.isEmpty()) {
            throw new ValidationException("Please select at least one valid course id to register or enter 'back' to go back to the previous page\n");
        }
        return registerCourseIds;
    }

    private ArrayList<Course> getAvailableCourses() throws SQLException, DatabaseException {
        if(availableCourses == null) {
            availableCourses = Server.getAvailableCourses(studentId, departmentId);
        }
        return availableCourses;
    }

    private void setMessage() throws SQLException, DatabaseException {
        String departmentName = Server.getDepartmentName(departmentId);
        message = String.format("These are available courses for you from department %s\n", departmentName) +
                  "You can register one or more courses by entering their id separated by space\n" +
                  "Enter 'back' to go back to the previous menu\n" +
                  "Enter 'logout' to logout from the system\n";
        super.setMessage(message);
    }
}
