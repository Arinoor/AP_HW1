package Page;

import Exception.*;
import System.*;

public class StudentLoginPage extends Page {

    private static final String message = """
            Welcome to Student Login page
            Please enter you student number and password
            Enter 'back' to go back to the previous menu
            Enter 'cancel' to restart the process
            """;

    public StudentLoginPage() {
        super(message);
        showMessage();
        run();
    }

    public void run() {
        try {
            int studentId = getStudentId();
            String password = getPassword();
            Server.loginStudent(studentId, password);
            new StudentHomePage(studentId);
        } catch (NavigationBackException e) {
            new LoginPage();
        } catch (NavigationCancelException e) {
            showMessage("\nLogin again\n");
            run();
        } catch (ValidationException e) {
            showMessage("\n" + e.getMessage() + "\n");
            run();
        } catch (Exception e) {
            showMessage("\nUnexpected error occurred\n" + e.getMessage() + "\n");
            run();
        }
    }

    private int getStudentId() throws NavigationException, ValidationException {
        String input = getInput("Enter your student number: ");
        checkBackOrCancel(input);
        Validation.validateStudentId(input);
        return Integer.parseInt(input);
    }

    private String getPassword() throws NavigationException, ValidationException {
        String input = getInput("Enter your password: ");
        checkBackOrCancel(input);
        Validation.validatePassword(input);
        return input;
    }


}
