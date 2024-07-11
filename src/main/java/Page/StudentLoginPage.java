package Page;

import Exception.*;

public class StudentLoginPage extends Page {

    private static String message = "Welcome to Student Login page\n" +
            "Please enter you student number and password\n" +
            "Enter 'back' to go back to the previous menu\n" +
            "Enter 'cancel' to restart the process\n";

    public StudentLoginPage() {
        super(message);
        showMessage();
        run();
    }

    public void run() {
        try {
            int studentId = getStudentId();
            String password = getPassword();
            // TODO ask server to log in user
            new StudentHomePage(studentId);

        } catch (NavigationBackException e) {
            new LoginPage();
        } catch (NavigationCancelException e) {
            showMessage("\nLogin again\n");
            run();
        } catch (Exception e) {
            showMessage(e.getMessage() + "\n");
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
