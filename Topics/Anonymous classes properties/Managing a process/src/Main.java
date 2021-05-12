import java.util.Scanner;

public class Main {

    private static String message;
    private static int errorCode;

    public static void main(String[] args) {

        final Scanner scanner = new Scanner(System.in);
        message = scanner.nextLine();
        errorCode = Integer.parseInt(scanner.nextLine());

        startLongProcess(/* pass your callback here */);

    }

    public static void startLongProcess(Callback callback) {
        callback.onStarted();
        callback.onStopped(message);
        callback.onStarted();
        callback.onFinished(errorCode);
    }

}

interface Callback {

    void onStarted();

    void onStopped(String cause);

    void onFinished(int code);
}