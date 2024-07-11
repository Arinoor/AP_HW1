package Exception;

public class NavigationCancelException extends NavigationException {

    public NavigationCancelException() {
        super();
    }

    public NavigationCancelException(String message) {
        super(message);
    }
}
