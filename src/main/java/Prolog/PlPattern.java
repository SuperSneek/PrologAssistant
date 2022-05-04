package Prolog;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PlPattern {

    protected String name;
    static Pattern rule = Pattern.compile("(.*) :- (.*)");

    public boolean matches(Term match) {
        return name.equals(match.getName());
    }

    public abstract Map<String, Term> findSolution();

    public static PlPattern textToPattern(String input, PrologEnv env) throws IllegalArgumentException {
        Matcher ruleMatcher = rule.matcher(input);
        if (ruleMatcher.find()) {
            Term left = Term.textToTerm(ruleMatcher.group(0), env);
            Term right = Term.textToTerm(ruleMatcher.group(1), env);
            return new Rule(left, right);
        } else {
            return Term.textToTerm(input, env);
        }
    }
}
