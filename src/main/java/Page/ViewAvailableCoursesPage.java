package Page;

import Exception.*;
import Model.Course;

import java.util.ArrayList;

public class ViewAvailableCoursesPage extends Page {

    private String message;
    private int studentId;
    private int departmentId;

    public ViewAvailableCoursesPage(int studentId, int departmentId) {
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
            showMessage(e.getMessage() + "\n");
            run();
        }
    }

    private void showAvailableCourses() {
        ArrayList<Course> availableCourses = Server.getAvailableCourses(studentId, departmentId);
        showCourses(availableCourses);
    }

    private ArrayList<Integer> getRegisterCourseIds() throws NavigationBackException {
        String input = getInput("Enter course ids to register: ");
        checkBack(input);
        String[] ids = input.split(" ");
        ArrayList<Integer> registerCourseIds = new ArrayList<>();
        for(String id: ids) {
            //TODO: add validation for course id
            registerCourseIds.add(Integer.parseInt(id));
        }
        return registerCourseIds;
    }

    private void setMessage() {
        String departmentName = Server.getDepartmentName(departmentId);
        message = String.format("These are available courses for you from department %s\n", departmentName) +
                "You can register one or more courses by entering their id separated by space\n" +
                "Enter 'back' to go back to the previous menu\n";
    }
}
