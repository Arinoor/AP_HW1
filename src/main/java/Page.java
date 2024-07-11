import java.io.IOException;
import java.util.Scanner;

public abstract class Page implements Runnable {
    protected String message;
    protected Scanner scanner = new Scanner(System.in);

    public Page(){
        clearConsole();
    }

    public Page(String message) {
        clearConsole();
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

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void invalidChoice() {
        showMessage("Invalid choice\n");
    }

    public void checkBackOrCancel(String choice) throws NavigationBackException, NavigationCancelException {
        checkBack(choice);
        checkCancel(choice);
    }

    public void checkBack(String choice) throws NavigationBackException {
        if(choice.equals("back")) {
            throw new NavigationBackException();
        }
    }

    public void checkCancel(String choice) throws NavigationCancelException {
        if(choice.equals("cancel")) {
            throw new NavigationCancelException();
        }
    }

    public void clearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name"); // Check the operating system

            if (operatingSystem.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error clearing console: " + e.getMessage());
    }
}
}
