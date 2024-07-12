package Page;

import Exception.*;
import System.*;

public class AdminLoginPage extends Page {

    private static final String message = """
            Welcome to Admin Login page
            Please enter your username and password
            Enter 'back' to go back to the previous menu
            Enter 'cancel' to restart the process
            """;

    public AdminLoginPage() {
        super(message);
        showMessage();
        run();
    }

    public void run() {

        try {
            String username = getUsername();
            String password = getPassword();
            Server.loginAdmin(username, password);
            new AdminHomePage();
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


    private String getUsername() throws NavigationException, ValidationException {
        String input = getInput("Enter your username: ");
        checkBackOrCancel(input);
        Validation.validateUsername(input);
        return input;
    }

    private String getPassword() throws NavigationException, ValidationException {
        String input = getInput("Enter your password: ");
        checkBackOrCancel(input);
        Validation.validatePassword(input);
        return input;
    }

}
