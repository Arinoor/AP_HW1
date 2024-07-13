package Page;

public class HomePage extends Page {

    private static final String message = """
            Welcome to course registration system
            Please login or register
            [1] Login
            [2] Register
            """;

    public HomePage() {
        super(message);
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