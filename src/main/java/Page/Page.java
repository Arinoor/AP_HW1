package Page;

import java.util.ArrayList;
import java.util.Scanner;
import Exception.*;
import Model.Course;

public abstract class Page implements Runnable {
    protected String message;
    protected Scanner scanner = new Scanner(System.in);

    public Page(){
        System.out.println();
    }

    public Page(String message) {
        System.out.println();
        this.message = message;
    }

    public void showMessage() {
        System.out.println(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChoice() {
        System.out.print("Enter your choice: ");
        return scanner.nextLine().toLowerCase().trim();
    }

    public String getInput(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public static void showMessage(String message) {
        System.out.println(message);
    }

    public static void invalidChoice() {
        showMessage("Invalid choice\n");
    }

    public static void checkBackOrCancel(String choice) throws NavigationBackException, NavigationCancelException {
        checkBack(choice);
        checkCancel(choice);
    }

    public static void checkBack(String choice) throws NavigationBackException {
        if(choice.equals("back")) {
            throw new NavigationBackException();
        }
    }

    public static void checkCancel(String choice) throws NavigationCancelException {
        if(choice.equals("cancel")) {
            throw new NavigationCancelException();
        }
    }

    public static void showCourses(ArrayList<Course> courses) {
        for(Course course: courses) {
            showMessage(course.toString());
        }
    }

}
