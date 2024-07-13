package Page;

import java.util.ArrayList;
import java.util.Scanner;
import Exception.*;
import Model.Course;

public abstract class Page implements Runnable {
    private String message;
    private Scanner scanner = new Scanner(System.in);

    public Page(){

    }

    public Page(String message) {
        this.message = message;
        showMessage();
    }

    public void showMessage() {
        System.out.println("\n---------------------------------------------\n");
        System.out.println(message);
    }

    public static void showMessage(String message) {
        System.out.println("\n" + message + "\n");
    }

    public String getInput(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public String getChoice(String message) {
        System.out.print(message);
        return scanner.nextLine().toLowerCase().trim();
    }

    public String getChoice() {
        return getChoice("Enter your choice: ");
    }

    public static void invalidChoice() {
        showMessage("Invalid choice, please try again");
    }

    public static void checkNavigation(String choice) throws NavigationException {
        checkBackOrCancel(choice);
        checkLogout(choice);
    }

    public static void checkBackOrCancel(String choice) throws NavigationBackException, NavigationCancelException {
        checkBack(choice);
        checkCancel(choice);
    }

    public static void checkBack(String choice) throws NavigationBackException {
        if(choice.trim().toLowerCase().equals("back")) {
            throw new NavigationBackException();
        }
    }

    public static void checkCancel(String choice) throws NavigationCancelException {
        if(choice.trim().toLowerCase().equals("cancel")) {
            throw new NavigationCancelException();
        }
    }

    public static void checkLogout(String choice) throws NavigationLogoutException {
        if(choice.trim().toLowerCase().equals("logout")) {
            throw new NavigationLogoutException();
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
