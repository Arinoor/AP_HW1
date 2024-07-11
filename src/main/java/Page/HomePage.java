package Page;

public class HomePage extends Page {

    private static String message = "Welcome to course registration system\n" +
            "Please login or register\n" +
            "[1] Login\n" +
            "[2] Register\n";

    public HomePage() {
        super(message);
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
                invalidChoice();
                run();
                break;
        }
    }

}