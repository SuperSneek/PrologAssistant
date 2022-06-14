import Prolog.PrologEnv;
import Prolog.Query;
import Prolog.Term;
import Prolog.Unification.UnificationFailureException;

import java.io.Console;
import java.io.InputStream;
import java.util.Scanner;

public class CommandLineInterface {

    static PrologEnv env = new PrologEnv();


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        boolean running = true;
        while (running) {
            String input = scanner.next();
            if(input.equals("quit")) {
                break;
            }
            if(input.startsWith("?")) {
                try {
                    Query q  = env.Query(Term.textToTerm(input.substring(1)));
                    while(q.hasNext()) {
                        System.out.println("true: " + q.next());
                        String s = scanner.next();
                        if(!s.equals(";")) {
                            break;
                        }
                    }
                    System.out.println("false");
                } catch (UnificationFailureException e) {
                    System.out.println("false");
                }
                continue;
            }
            try {
                env.LoadPattern(input);
                System.out.println(env);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
