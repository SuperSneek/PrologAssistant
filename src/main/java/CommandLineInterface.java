import Prolog.PrologEnv;
import Prolog.Query;
import Prolog.Terms.Term;
import Prolog.Unification.UnificationFailureException;

import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineInterface {

    static PrologEnv env = new PrologEnv();

    static Pattern metaCommand = Pattern.compile(":(.*)");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        boolean running = true;
        while (running) {
            String input = scanner.next();
            if(input.equals("quit")) {
                break;
            }
            Matcher m = metaCommand.matcher(input);
            if(m.matches()) {
                String s = m.group(1);
                switch (s) {
                    case "l":
                        System.out.println("Please enter filename (relative to running program):");
                        String fn = scanner.next();
                        URL ressource =  CommandLineInterface.class.getResource(fn);
                        if(ressource == null) {
                            System.out.println("Ressource does not exist");
                        }
                        try {
                            env.LoadModule(ressource);
                        } catch(IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }

                    case "p":
                        System.out.println(env);
                    default:
                        continue;
                }
            }
            if(input.startsWith("?")) {
                try {
                    try{
                        Term.textToTerm(input.substring(1));
                    } catch (IllegalArgumentException e) {
                        System.out.println(input.substring(1) + " is not a term.");
                        continue;
                    }
                    Query q = env.Query(Term.textToTerm(input.substring(1)));;
                    while(q.hasNext()) {
                        Map<String, Term> answer = q.next();
                        if(answer == null) {
                            break;
                        }
                        System.out.println("true: " + answer);
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
