package Prolog;

import Prolog.Unification.Unifier;

import java.io.File;
import java.util.*;

public class PrologEnv extends Thread {

    private Unifier currentUnifier;

    List<Term> loadedTerms = new LinkedList<>();

    public void LoadModule(File file) {

    }

    public String ProcessWords(String[] words) {
        Stack<Term> s = new Stack<>();
        s.addAll(Arrays.stream(words).map(Atom::new).toList());
        PList l = PList.fromQueue(s);
        return Query(new Compound("pattern", new Term[] {l, new Variable("RESULT", this)}));
    }

    public String Query(Term query) {
        return "";
    }



}
