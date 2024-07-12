package Page;

public class StudentHomePage extends Page {

    private static final String message = """
            Welcome to Student Home page
            Please choose an option
            [1] View registered courses
            [2] View available courses
            [3] Logout
            """;

    private final int studentId;

    public StudentHomePage(int studentId) {
        super(message);
        this.studentId = studentId;
        showMessage();
        run();
    }

    public void run() {
        String choice = getChoice();
        if(choice.equals("1") || choice.contains("registered")){
            new ViewRegisteredCoursesPage(studentId);
        } else if(choice.equals("2") || choice.contains("available")){
            new ChooseDepartmentToViewAvailableCoursesPage(studentId);
        } else if(choice.equals("3") || choice.contains("logout")){
            new HomePage();
        } else {
            invalidChoice();
            run();
        }
    }

    public int getStudentId() {
        return studentId;
    }
}
