package Page;

public class LoginPage extends Page {

    private static String message = "Welcome to Login page\n" +
            "Please choose to enter as student or admin\n" +
            "[1] Student\n" +
            "[2] Admin\n" +
            "[3] Back\n";

    public LoginPage() {
        super(message);
        showMessage();
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
