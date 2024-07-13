package Page;

import Model.Department;

import java.sql.SQLException;
import java.util.ArrayList;
import System.*;
import Exception.*;

public class AdminHomePage extends Page {

    private static final String message = """
            Welcome to Admin Home page
            Please choose an department by its id to view its courses
            Enter 'logout' to logout from the system
            """;

    public AdminHomePage() {
        super(message);
        run();
    }

    public void run() {

        try {
            showDepartments();
            int departmentID = getDepartmentID();
            new ViewCoursesAdminPage(departmentID);
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

    int getDepartmentID() throws NavigationException, ValidationException, SQLException {
        String input = getInput("Enter department id: ");
        checkLogout(input);
        Validation.validateDepartmentId(input);
        return Integer.parseInt(input);
    }

    private void showDepartments() throws SQLException {
        ArrayList<Department> departments = Server.getDepartments();
        Department.showDepartments(departments);
    }
}
