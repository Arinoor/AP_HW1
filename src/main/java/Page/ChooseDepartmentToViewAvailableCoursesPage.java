package Page;

import java.sql.SQLException;
import java.util.ArrayList;
import Exception.*;
import Model.*;
import System.*;

public class ChooseDepartmentToViewAvailableCoursesPage extends Page {

    private static final String message = """
            Choose a department by its id to view its available courses
            Enter 'back' to go back to the previous menu
            """;

    private final int studentId;

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
        } catch (ValidationException e) {
            showMessage("\n" + e.getMessage() + "\n");
            run();
        } catch (Exception e) {
            showMessage("\nUnexpected error occurred\n" + e.getMessage() + "\n");
            run();
        }
    }

    private int getDepartmentId() throws NavigationBackException, ValidationException, SQLException {
        String input = getInput("Enter department id: ");
        checkBack(input);
        Validation.validateDepartmentId(input);
        return Integer.parseInt(input);
    }

    private void showDepartments() throws SQLException {
        ArrayList<Department> departments = Server.getDepartments();
        for(Department department : departments) {
            showMessage(department.toString());
        }
    }
}
