import java.util.Locale;

public class HomePage extends Page {

    private String message = "Welcome to course registration system\n" +
            "Please login or register\n" +
            "[1] Login\n" +
            "[2] Register\n\n";

    public HomePage() {
        showMessage();
        run();
    }

    public void run() {
        String choice = getChoice();
        switch (choice) {
            case "1": case "login":
                new LoginPage();
                break;
            case "2": case "register":
                new RegisterPage();
                break;
            default:
                System.out.println("Invalid choice");
                run();
                break;
        }
    }

}