import java.io.Console;
import java.io.InputStream;
import java.util.Scanner;

public class CommandLineInterface {

    public static void main(String[] args) {
        Console console = System.console();
        Scanner scanner = new Scanner(console.reader());
        boolean running = true;
        while (running) {
            String input = scanner.next();
            running = false;
        }
    }

}
