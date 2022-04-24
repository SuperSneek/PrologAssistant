import Prolog.Term;

import java.io.Console;
import java.io.InputStream;
import java.util.Scanner;

public class CommandLineInterface {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            String input = scanner.next();
            try {
                System.out.println(Term.textToTerm(input));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            running = false;
        }
    }

}
