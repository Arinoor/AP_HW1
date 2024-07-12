package Page;

import Exception.*;

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
            // TODO ask server to register user
            new StudentHomePage(studentId);

        } catch (NavigationBackException e) {
            new HomePage();
        } catch (NavigationCancelException e) {
            showMessage("\nRegister again\n");
            run();
        } catch (Exception e) {
            showMessage("\n" + e.getMessage() + "\n");
            run();
        }
    }

    private int getStudentId() throws NavigationException {
        String input = getInput("Enter your student number: ");
        checkBackOrCancel(input);
        // TODO: add validation student number
        return Integer.parseInt(input);
    }

    private String getPassword() throws NavigationException {
        String input = getInput("Enter your password: ");
        checkBackOrCancel(input);
        // TODO: add validation password
        return input;
    }

}
