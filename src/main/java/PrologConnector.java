import org.jpl7.JPL;
import org.jpl7.Query;
import org.jpl7.Term;

public class PrologConnector {

    public PrologConnector() {
        Term whereRule1 = Term.textToTerm("where(\"Where\"|X))");
        Term whereRule2 = Term.textToTerm("where(Y|X)) :- where(X)");
    }

    public void ProcessCommand(String[] words) {
        if(words[1].toUpperCase().equals("WHERE")) {
            Term goal = Term.textToTerm("where(X)");
            Query q = new Query(goal);
        }
    }

}
