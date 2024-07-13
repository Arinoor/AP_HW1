package Page;

public class ViewCourseAdminPage extends Page {

    private static final String message = """
            These are students enrolled in this course
            Choose one option:
            [1] register new students
            [2] drop registered students
            [3] increase capacity
            [4] back
            [5] logout
            """;

    private final int courseId;
    private final int departmentId;

    public ViewCourseAdminPage(int courseId, int departmentId) {
        super(message);
        this.courseId = courseId;
        this.departmentId = departmentId;
        run();
    }

    public void run() {
        String choice = getChoice("Enter your choice: ");
        if(choice.equals("5") || choice.equals("logout")) {
            new HomePage();
        }
        else if(choice.equals("4") || choice.equals("back")) {
            try {
                new ViewCoursesAdminPage(departmentId);
            } catch (Exception e) {
                showExceptionMessage(e);
                run();
            }
        }
        else if(choice.equals("1") || choice.contains("register")) {
            new registerStudentAdminPage(courseId, departmentId);
        }
        else if(choice.equals("2") || choice.contains("drop")) {
            new dropStudentAdminPage(courseId, departmentId);
        }
        else if(choice.equals("3") || choice.contains("capacity")) {
            new IncreaseCapacityPage(courseId, departmentId);
        }
        else {
            invalidChoice();
            run();
        }
    }
}
