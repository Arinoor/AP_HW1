package Page;

import Exception.*;
import System.*;

public class RegisterPage extends Page {

    private static final String message = """
            Welcome to Register page
            Please enter you student number and password
            Enter 'back' to go back to the previous menu
            Enter 'cancel' to restart the process
            """;

    public RegisterPage() {
        super(message);
        showMessage();
        run();
    }

    public void run() {
        try {
            int studentId = getStudentId();
            String password = getPassword();
            Server.register(studentId, password);
            new StudentHomePage(studentId);
        } catch (NavigationBackException e) {
            new HomePage();
        } catch (NavigationCancelException e) {
            showMessage("\nRegister again\n");
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
