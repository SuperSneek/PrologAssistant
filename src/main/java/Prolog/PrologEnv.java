package Prolog;

import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

public class PrologEnv extends Thread {

    private Unifier currentUnifier;

    List<PlPattern> loadedPatterns = new LinkedList<>();

    public void LoadModule(File file) {

    }

    public void LoadPattern(String input) {
        PlPattern p = PlPattern.textToPattern(input, this);
        loadedPatterns.add(p);
    }

    public Query ProcessWords(String[] words) throws UnificationFailureException {
        Stack<Term> s = new Stack<>();
        s.addAll(Arrays.stream(words).map(Atom::new).toList());
        PList l = PList.fromQueue(s);
        return Query(new Compound("pattern", new Term[]{l, new Variable("RESULT")}));
    }

    public Query Query(Term query) throws UnificationFailureException {
        return new Query(query, this);
    }

    public Iterator<PlPattern> findMatchingPatterns(Term match) {
        return loadedPatterns.stream().filter(x -> (x.matches(match))).iterator();
    }

    @Override
    public java.lang.String toString() {
        StringBuilder sb = new StringBuilder();
        for (PlPattern p :
                loadedPatterns) {
            sb.append(p.toString()).append(", ");
        }
        return sb.toString();
    }
}
