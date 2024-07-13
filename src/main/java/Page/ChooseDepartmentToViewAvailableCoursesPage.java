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
            Enter 'logout' to logout from the system
            """;

    private final int studentId;

    public ChooseDepartmentToViewAvailableCoursesPage(int studentId) {
        super(message);
        this.studentId = studentId;
        run();
    }

    public void run() {
        try {
            showDepartments();
            int departmentId = getDepartmentId();
            new ViewAvailableCoursesPage(studentId, departmentId);
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

    private int getDepartmentId() throws NavigationException, ValidationException, SQLException {
        String input = getInput("Enter department id: ");
        checkBack(input);
        checkLogout(input);
        Validation.validateDepartmentId(input);
        return Integer.parseInt(input);
    }

    private void showDepartments() throws SQLException {
        ArrayList<Department> departments = Server.getDepartments();
        Department.showDepartments(departments);
    }
}
