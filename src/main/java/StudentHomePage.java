public class StudentHomePage extends Page {

    private String message = "Welcome to Student Home page\n" +
            "Please choose an option\n" +
            "[1] View registered courses\n" +
            "[2] View available courses\n" +
            "[3] Logout\n\n";

    private int studentId;

    public StudentHomePage(int studentId) {
        super();
        this.studentId = studentId;
        showMessage();
        run();
    }

    public void run() {
        String choice = getChoice();
        if(choice.equals("1") || choice.contains("registered")){
           // new ViewRegisteredCoursesPage(studentId);
        } else if(choice.equals("2") || choice.contains("available")){
            //new ViewAvailableCoursesPage(studentId);
        } else if(choice.equals("3") || choice.contains("logout")){
            new LoginPage();
        } else {
            invalidChoice();
            run();
        }
    }

    public int getStudentId() {
        return studentId;
    }
}
