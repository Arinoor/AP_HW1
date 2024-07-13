package Page;

public class LoginPage extends Page {

    private static final String message = """
            Welcome to Login page
            Please choose to enter as student or admin
            [1] Student
            [2] Admin
            [3] Back
            """;

    public LoginPage() {
        super(message);
        run();
    }

    public void run() {
        String choice = getChoice();
        switch (choice) {
            case "1": case "student":
                new StudentLoginPage();
                break;
            case "2": case "admin":
                new AdminLoginPage();
                break;
            case "3": case "back":
                new HomePage();
                break;
            default:
                invalidChoice();
                run();
                break;
        }
    }
}
