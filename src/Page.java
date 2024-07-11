import java.util.Scanner;

public abstract class Page implements Runnable {
    protected String message;
    protected Scanner scanner = new Scanner(System.in);

    public Page(){

    }

    public Page(String message) {
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
}
