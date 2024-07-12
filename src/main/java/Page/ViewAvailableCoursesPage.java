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

    public ViewAvailableCoursesPage(int studentId, int departmentId) throws SQLException, DataBaseException {
        super();
        setMessage();
        showMessage(message);
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
        } catch (Exception e) {
            showMessage("\nUnexpected error occurred\n" + e.getMessage() + "\n");
            run();
        }
    }

    private void showAvailableCourses() throws SQLException, DataBaseException {
        ArrayList<Course> availableCourses = getAvailableCourses();
        showCourses(availableCourses);
    }

    private ArrayList<Integer> getRegisterCourseIds() throws NavigationBackException, ValidationException {
        String input = getInput("Enter course ids to register: ");
        checkBack(input);
        String[] Ids = input.split(" ");
        ArrayList<Integer> registerCourseIds = Validation.getValidatedRegisterCourseIds(Ids, availableCourses);
        if(registerCourseIds.isEmpty()) {
            throw new ValidationException("Please select at least one valid course id to register or enter 'back' to go back to the previous page\n");
        }
        return registerCourseIds;
    }

    private void setMessage() throws SQLException, DataBaseException {
        String departmentName = Server.getDepartmentName(departmentId);
        message = String.format("These are available courses for you from department %s\n", departmentName) +
                "You can register one or more courses by entering their id separated by space\n" +
                "Enter 'back' to go back to the previous menu\n";
    }

    private ArrayList<Course> getAvailableCourses() throws SQLException, DataBaseException {
        if(availableCourses == null) {
            availableCourses = Server.getAvailableCourses(studentId, departmentId);
        }
        return availableCourses;
    }
}
