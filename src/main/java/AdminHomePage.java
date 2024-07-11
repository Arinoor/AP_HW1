public class AdminHomePage extends Page {

    private String message = "Welcome to Admin Home page\n" +
            "Please choose an option\n" +
            "[1] View courses\n" +
            "[2] Logout\n\n";

    public AdminHomePage() {
        super();
        showMessage();
        run();
    }

    public void run() {
        String choice = getChoice();
        if(choice.equals("1") || choice.contains("courses")){
            // new ViewCoursesPage();
        } else if(choice.equals("2") || choice.contains("logout")){
            new LoginPage();
        } else {
            invalidChoice();
            run();
        }
    }
}
