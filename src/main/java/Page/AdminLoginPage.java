package Page;

import Exception.*;

public class AdminLoginPage extends Page {

    private static String message = "Welcome to Admin Login page\n" +
            "Please enter your username and password\n" +
            "Enter 'back' to go back to the previous menu\n" +
            "Enter 'cancel' to restart the process\n";

    public AdminLoginPage() {
        super(message);
        showMessage();
        run();
    }

    public void run() {

        try {
            String username = getUsername();
            String password = getPassword();
            // TODO ask server to log in admin
            new AdminHomePage();
        } catch (NavigationBackException e) {
            new LoginPage();
        } catch (NavigationCancelException e) {
            showMessage("\nLogin again\n");
            run();
        } catch (Exception e) {
            showMessage(e.getMessage());
            run();
        }
    }


    private String getUsername() throws NavigationException {
        String input = getInput("Enter your username: ");
        checkBackOrCancel(input);
        // TODO : check username validation
        return input;
    }

    private String getPassword() throws NavigationException {
        String input = getInput("Enter your password: ");
        checkBackOrCancel(input);
        // TODO : check password validation
        return input;
    }

}
