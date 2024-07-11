package Page;

import Model.Course;
import java.util.ArrayList;
import Exception.*;

public class ViewRegisteredCoursesPage extends Page {

    private static String message = "There are your registered courses\n" +
            "You can drop one or more courses by entering their id separated by space\n" +
            "Enter 'back' to go back to the previous page\n";

    private int studentId;

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
        } catch (Exception e) {
            showMessage(e.getMessage() + "\n");
            run();
        }
    }

    public ArrayList<Integer> getDropCourseIds() throws NavigationBackException {
        String input = getInput("Enter course ids to drop: ");
        checkBack(input);
        String[] ids = input.split(" ");
        ArrayList<Integer> dropCourseIds = new ArrayList<>();
        for(String id: ids) {
            //TODO: add validation for course id
            dropCourseIds.add(Integer.parseInt(id));
        }
        return dropCourseIds;
    }

    private void showRegisteredCourses() {
        ArrayList<Course> registeredCourses = Server.getCoursesRegisteredByStudent(studentId);
        showCourses(registeredCourses);
    }
}
