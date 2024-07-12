package Page;

import java.util.ArrayList;
import Exception.*;

public class ChooseDepartmentToViewAvailableCoursesPage extends Page {

    private static String message = "Choose a department by its id to view its available courses\n" +
            "Enter 'back' to go back to the previous menu\n";

    private int studentId;

    public ChooseDepartmentToViewAvailableCoursesPage(int studentId) {
        super(message);
        this.studentId = studentId;
        showMessage();
        run();
    }

    public void run() {
        try {
            showDepartments();
            int departmentId = getDepartmentId();
            new ViewAvailableCoursesPage(studentId, departmentId);
        } catch (NavigationBackException e) {
            new StudentHomePage(studentId);
        } catch (Exception e) {
            showMessage(e.getMessage() + "\n");
            run();
        }
    }

    private int getDepartmentId() throws NavigationBackException {
        String input = getInput("Enter department id: ");
        checkBack(input);
        //TODO: add validation for department id
        return Integer.parseInt(input);
    }

    private void showDepartments() {
        ArrayList<Department> departments = Server.getDepartments();
        for(Department department : departments) {
            showMessage(department.toString());
        }
    }
}
