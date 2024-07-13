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
            showMessage("Login again");
            run();
        } catch (ValidationException e) {
            showMessage(e.getMessage());
            run();
        } catch (Exception e) {
            showExceptionMessage(e);
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
