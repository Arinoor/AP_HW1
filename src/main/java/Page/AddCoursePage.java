package Page;

import Model.CourseType;

import Model.*;
import Exception.*;
import System.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class AddCoursePage extends Page {

    private static final String message = """
            Enter course details
            Enter 'back' to go back to the previous page
            Enter 'cancel' to restart the process
            Enter 'logout' to logout from the system
            """;
    private final int departmentID;

    public AddCoursePage(int departmentID) {
        super(message);
        this.departmentID = departmentID;
        run();
    }

    public void run() {
        try {
            int courseId = getCourseId();
            String instructorName = getInstructorName();
            String courseName = getCourseName();
            int capacity = getCapacity();
            int credits = getCredits();
            int numberOfClassTimes = getNumberOfClassTimes();
            ArrayList<ClassTime> classTimes = getClassTimes(numberOfClassTimes);
            Date examStartTime = getExamStartTime();
            CourseType courseType = getCourseType();

            Course course = new Course(courseId, instructorName, courseName, capacity, credits, classTimes, examStartTime, courseType, departmentID);
            Server.addCourse(course);
            new ViewCoursesAdminPage(departmentID);

        } catch (NavigationBackException e) {
            try {
                new ViewCoursesAdminPage(departmentID);
            } catch (Exception exception){
                showMessage("Unexpected error occurred\n" + exception.getMessage());
                run();            }
        } catch (NavigationCancelException e) {
            showMessage("Add again");
            run();
        } catch (NavigationLogoutException e) {
            new HomePage();
        } catch (ValidationException e) {
            showMessage(e.getMessage());
            run();
        } catch (Exception e) {
            showMessage("Unexpected error occurred\n" + e.getMessage());
            run();
        }
    }

    private int getCourseId() throws NavigationException, ValidationException {
        String input = getInput("Enter course id: ");
        checkNavigation(input);
        Validation.validateNewCourseId(input);
        return Integer.parseInt(input);
    }

    private String getInstructorName() throws NavigationException {
        String input = getInput("Enter instructor name: ");
        checkNavigation(input);
        Validation.validateNewInstructorName(input);
        return input;
    }

    private String getCourseName() throws NavigationException {
        String input = getInput("Enter course name: ");
        checkNavigation(input);
        Validation.validateNewCourseName(input, departmentID);
        return input;
    }

    private int getCapacity() throws NavigationException {
        String input = getInput("Enter capacity: ");
        checkNavigation(input);
        Validation.validateCapacity(input);
        return Integer.parseInt(input);
    }

    private int getCredits() throws NavigationException {
        String input = getInput("Enter credits: ");
        checkNavigation(input);
        Validation.validateCredits(input);
        return Integer.parseInt(input);
    }

    private int getNumberOfClassTimes() throws NavigationException {
        String input = getInput("Enter number of class times: ");
        checkNavigation(input);
        Validation.validateNumberOfClassTimes(input);
        return Integer.parseInt(input);
    }

    private ArrayList<ClassTime> getClassTimes(int numberOfClassTimes) throws NavigationException {
        ArrayList<ClassTime> classTimes = new ArrayList<>();
        for (int i = 0; i < numberOfClassTimes; i++) {
            Day day = getDay();
            LocalTime startTime = getStartTime();
            LocalTime endTime = getEndTime();
            ClassTime classTime = new ClassTime(day, startTime, endTime);
            Validation.validateClassTime(classTime);
            classTimes.add(classTime);
        }
        return classTimes;
    }

    private Day getDay() throws NavigationException {
        String input = getInput("Enter day (first two letters): ");
        checkNavigation(input);
        return Validation.validateDay(input);
    }

    private LocalTime getStartTime() throws NavigationException {
        String input = getInput("Enter start time in hh:mm format: ");
        checkNavigation(input);
        return Validation.validateStartTime(input);
    }

    private LocalTime getEndTime() throws NavigationException {
        String input = getInput("Enter end time in hh:mm format: ");
        checkNavigation(input);
        return Validation.validateEndTime(input);
    }

    private Date getExamStartTime() throws NavigationException {
        String input = getInput("Enter exam start time in yyyy-MM-dd hh:mm format: ");
        checkNavigation(input);
        return Validation.validateExamStartTime(input);
    }

    private CourseType getCourseType() throws NavigationException {
        String input = getInput("Enter course type (G for General / S for Specialized): ");
        checkNavigation(input);
        return Validation.validateCourseType(input);
    }

}
