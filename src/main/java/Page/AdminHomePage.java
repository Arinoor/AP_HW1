package Page;

public class AdminHomePage extends Page {

    private static final String message = """
            Welcome to Admin Home page
            Please choose an option
            [1] View courses
            [2] Logout
            """;

    public AdminHomePage() {
        super(message);
        showMessage();
        run();
    }

    public void run() {
        String choice = getChoice();
        if(choice.equals("1") || choice.contains("view")){
            new ViewCoursesPage();
        } else if(choice.equals("2") || choice.contains("logout")){
            new LoginPage();
        } else {
            invalidChoice();
            run();
        }
    }
}
