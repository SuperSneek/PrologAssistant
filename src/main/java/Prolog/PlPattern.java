package Prolog;

import Prolog.Unification.UnificationFailureException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PlPattern {

    protected String name;
    static Pattern rule = Pattern.compile("(.*):-(.*)");

    public boolean matches(Term match) {
        return name.equals(match.getName());
    }

    public abstract Map<String, Term> unify(Term queryTerm, PrologEnv env, Map<String, Term> vars) throws UnificationFailureException;

    public static PlPattern textToPattern(String input, PrologEnv env) throws IllegalArgumentException {
        Matcher ruleMatcher = rule.matcher(input);
        if (ruleMatcher.find()) {
            Term left = Term.textToTerm(ruleMatcher.group(1));
            Term right = Term.textToTerm(ruleMatcher.group(2));
            return new Rule(left, right);
        } else {
            return Term.textToTerm(input);
        }
    }
}
