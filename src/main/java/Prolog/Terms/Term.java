package Prolog.Terms;

import Prolog.PlPattern;
import Prolog.PrologEnv;
import Prolog.Solution;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public abstract class Term extends PlPattern {

    public abstract String toString();

    public abstract UnificationClauseCarrier generateClauses(Term other) throws UnificationFailureException;

    public abstract boolean equals(Term other);

    public abstract Term substitute(Map<String, Term> vars);

    public String getName() {return name;}

    static Pattern not = Pattern.compile("(not)\\((.+)\\)");
    static Pattern compound = Pattern.compile("([a-z]+)\\((.+)\\)");
    static Pattern list = Pattern.compile("\\[([^.]+\\.?)*\\]");
    static Pattern var = Pattern.compile("(?:[A-Z]|_)+");
    static Pattern atom = Pattern.compile("[a-z]+");



    @Override
    public Solution unify(Term queryTerm, PrologEnv env, Map<String, Term> vars) throws UnificationFailureException {
        Unifier u = new Unifier(this, queryTerm, vars);
        return u;
    }

    /**
     * @param input String to convert
     * @return Created Term
     * @throws IllegalArgumentException String Is not a valid Term
     */
    public static Term textToTerm(String input) throws IllegalArgumentException {
        Matcher compoundMatcher = compound.matcher(input);
        Matcher varMatcher = var.matcher(input);
        Matcher atomMatcher = atom.matcher(input);
        Matcher listMatcher = list.matcher(input);
        Matcher notMatcher = not.matcher(input);
        if(notMatcher.matches()) {
            return matchNot(notMatcher);
        }
        else if(compoundMatcher.matches()) {
            return matchCompound(compoundMatcher);
        } else if(listMatcher.matches()) {
            return matchList(listMatcher, input);
        } else if(atomMatcher.matches()) {
            return new Atom(input);
        } else if(varMatcher.matches()) {
            if(input.equals("_")) {
                return new DontCare();
            }
            return new Variable(input);
        }
        throw new IllegalArgumentException("Not a Term");
    }

    private static Term matchNot(Matcher notMatcher) {
        String args = notMatcher.group(2);
        return new Not(Term.textToTerm(args));
    }

    private static Compound matchCompound(Matcher compoundMatcher) {
        String args = compoundMatcher.group(2);
        String[] values = args.split(",");
        Term[] terms = new Term[values.length];
        for (int i = 0; i < values.length; i++) {
            terms[i] = (Term.textToTerm(values[i]));
        }
        if(values.length == 0) {
            terms = new Term[1];
            terms[0] = Term.textToTerm(args);
        }
        return new Compound(compoundMatcher.group(1), terms);
    }

    private static PList matchList(Matcher listMatcher, String input) {
        String content = input.substring(1, input.length() - 1);
        String[] items;
        items = content.split("\\.");
        if(content.length() == 0) {
            return new PList(null);
        }
        PList list = new PList(Term.textToTerm(items[0]));
        PList acc = list;
        for (int i = 1; i < items.length; i++) {
            acc.next = new PList(Term.textToTerm(items[i]));
            acc = (PList) acc.next;
        }
        return list;
    }

    public boolean contains(Term c) {
        return false;
    }

}
