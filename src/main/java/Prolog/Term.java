package Prolog;

import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public abstract class Term extends PlPattern {

    public abstract String toString();

    public abstract UnificationClauseCarrier generateClauses(Term other) throws UnificationFailureException;

    public abstract boolean equals(Term other);

    public String getName() {return name;}


    static Pattern compound = Pattern.compile("([a-z]+)\\(((?:.+,?)+)\\)");
    static Pattern list = Pattern.compile("(\\[(?:[a-zA-Z]+,?)*\\])|(\\[(?:[A-Z]\\|[A-Z])\\])");
    static Pattern var = Pattern.compile("[A-Z]+");
    static Pattern atom = Pattern.compile("[a-z]+");

    @Override
    public Map<String, Term> unify(Term queryTerm, PrologEnv env,  Map<String, Term> vars) throws UnificationFailureException {
        Unifier u = new Unifier(this, queryTerm, vars);
        return u.next();
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
        if(compoundMatcher.matches()) {
            return matchCompound(compoundMatcher);
        } else if(listMatcher.matches()) {
            return matchList(listMatcher, input);
        } else if(atomMatcher.matches()) {
            return new Atom(input);
        } else if(varMatcher.matches()) {
            return new Variable(input);
        }
        throw new IllegalArgumentException("Not a Term");
    }

    private static Compound matchCompound(Matcher compoundMatcher) {
        String args = compoundMatcher.group(2);
        String[] values = args.split(",");
        Term[] terms = new Term[values.length];
        for (int i = 0; i < values.length; i++) {
            terms[i] = (Term.textToTerm(values[i]));
        }
        return new Compound(compoundMatcher.group(1), terms);
    }

    private static PList matchList(Matcher listMatcher, String input) {
        String content = input.replaceAll("\\[?\\]?", "");
        String[] items;
        if(input.contains("|")) {
            items = content.split("\\|");
            if(items.length != 2) {
                throw new IllegalArgumentException("Not a Term");
            } else {
                return new PList(Term.textToTerm(items[0]), new PList(Term.textToTerm(items[1])));
            }
        } else{
            items = content.split(",");
        }
        if(content.length() == 0) {
            return new PList(null);
        }
        PList list = new PList(Term.textToTerm(items[0]));
        PList acc = list;
        for (int i = 1; i < items.length; i++) {
            acc.next = new PList(Term.textToTerm(items[i]), null);
            acc = (PList) acc.next;
        }
        return list;
    }

}
