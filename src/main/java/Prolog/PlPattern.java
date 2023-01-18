package Prolog;

import Prolog.Rule.Rule;
import Prolog.Terms.PList;
import Prolog.Terms.Term;
import Prolog.Terms.Variable;
import Prolog.Unification.UnificationFailureException;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PlPattern {

    protected String name;
    static Pattern rule = Pattern.compile("(.*):-(.*)");

    public boolean matches(Term match) {
        return name.equals(match.getName()) || (match instanceof Variable);
    }

    public abstract Solution unify(Term queryTerm, PrologEnv env, Map<String, Term> vars) throws UnificationFailureException;

    public static PlPattern textToPattern(String input, PrologEnv env) throws IllegalArgumentException {
        Matcher ruleMatcher = rule.matcher(input);
        if (ruleMatcher.find()) {
            Term left = Term.textToTerm(ruleMatcher.group(1));
            PList right = (PList) Term.textToTerm(ruleMatcher.group(2));
            return new Rule(left, right, env);
        } else {
            return Term.textToTerm(input);
        }
    }
}
