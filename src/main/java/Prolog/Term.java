package Prolog;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public abstract class Term {

    public abstract String toString();

    protected String name;

    static Pattern rule = Pattern.compile("(.*) :- (.*)");
    static Pattern compound = Pattern.compile("([a-z]+)([S],?)+");
    static Pattern var = Pattern.compile("[A-Z]*");
    static Pattern atom = Pattern.compile("[a-z]*");

    public abstract boolean hasSolution();

    public static Term textToTerm(String input) throws IllegalArgumentException {
        Matcher ruleMatcher = rule.matcher(input);
        Matcher compoundMatcher = compound.matcher(input);
        Matcher varMatcher = var.matcher(input);
        Matcher atomMatcher = atom.matcher(input);

        if (ruleMatcher.find()) {
            Term left = Term.textToTerm(ruleMatcher.group(0));
            Term right = Term.textToTerm(ruleMatcher.group(1));
            return new Rule(left, right);
        } else if(compoundMatcher.find()) {
            int count = compoundMatcher.groupCount();
            Term[] terms = new Term[count - 1];
            for (int i = 1; i < count; i++) {
                terms[i-1] = (Term.textToTerm(compoundMatcher.group(i)));
            }
            return new Compound(compoundMatcher.group(0), terms);
        } else if(atomMatcher.find()) {
            return new Atom(input);
        } else if(varMatcher.find()) {
            System.out.println(var);
        }
        throw new IllegalArgumentException("Not a Term");
    }

}
