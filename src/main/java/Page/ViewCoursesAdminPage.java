package Page;

import Model.*;
import System.*;
import Exception.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewCoursesAdminPage extends Page {

    private String message;
    private final int departmentID;
    private ArrayList<Integer> courseIDs;

    public ViewCoursesAdminPage(int departmentID) throws SQLException, DatabaseException {
        super();
        this.departmentID = departmentID;
        setMessage();
        showMessage();
        run();
    }

    public void run() {
        try {
            showCourses();
            String choice = getChoice(message);
            if(choice.equals("logout")) {
                new HomePage();
            }
            else if(choice.equals("add")) {
                new AddCoursePage(departmentID);
            }
            else {
                try {
                    Validation.validateCourseId(choice);
                    int courseId = Integer.parseInt(choice);
                    if(getCourseIDs().contains(courseId)) {
                        new ViewCourseAdminPage(courseId, departmentID);
                    }
                    else {
                        showMessage("Course not found");
                        run();
                    }
                } catch (ValidationException e) {
                    invalidChoice();
                    run();
                }
            }
        }
        catch (Exception e) {
            showMessage("Unexpected error occurred\n" + e.getMessage());
            run();
        }

    }

    private void showCourses() throws SQLException, DatabaseException {
        if(courseIDs.isEmpty()) {
            showMessage("No courses to show");
        }
        else {
            ArrayList<Course> courses = Server.getCoursesByIds(getCourseIDs());
            Course.showCourses(courses);
        }
    }

    private ArrayList<Integer> getCourseIDs() throws SQLException, DatabaseException {
        if(courseIDs == null) {
            courseIDs = Server.getCourseIds(departmentID);
        }
        return courseIDs;
    }

    public void setMessage() throws SQLException, DatabaseException {
        String departmentName = Server.getDepartmentName(departmentID);
        message = String.format("Presented courses in department %s", departmentName) + "\n" +
                "Select a course by its id to view its students or change capacity" + "\n" +
                "Enter 'add' to add a new course" + "\n" +
                "Enter 'logout' to logout from the system";
        super.setMessage(message);
    }
}
