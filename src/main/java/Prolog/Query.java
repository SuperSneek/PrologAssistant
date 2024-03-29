package Prolog;

import Prolog.Terms.Not;
import Prolog.Terms.Term;
import Prolog.Unification.UnificationFailureException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Query implements Solution {

    private final Iterator<PlPattern> patternIterator;
    private final PrologEnv env;
    private final Term query;
    private final Map<String, Term> vars;

    Solution current = new EmptySolution();

    public Query(Term queryTerm, PrologEnv env) {
        //TODO: multithread
        patternIterator = env.findMatchingPatterns(queryTerm);
        this.env = env;
        this.query = queryTerm;
        vars = new HashMap<>();
    }

    public Query(Term queryTerm, PrologEnv env, Map<String, Term> vars) {
        //TODO: multithread
        patternIterator = env.findMatchingPatterns(queryTerm);
        this.env = env;
        this.query = queryTerm.substitute(vars);
        this.vars = vars;
    }

    private Map<String, Term> unifyPattern() throws UnificationFailureException {
        if(current != null && !patternIterator.hasNext() && !current.hasNext()) {
            throw new UnificationFailureException();
        }
        if(current != null && current.hasNext()) {
            return current.next();
        }
        PlPattern pattern = patternIterator.next();
        current = pattern.unify(query, env, vars);
        return unifyPattern();
    }

    @Override
    public boolean hasNext() {
        return patternIterator.hasNext() || current.hasNext() || query instanceof Not;
    }

    @Override
    public Map<String, Term> next() {
        try {
            Map<String, Term> result = unifyPattern();
            if(result == null) {
                throw new UnificationFailureException();
            }
            if(query instanceof Not) {
                return null;
            }
            return result;
        } catch (UnificationFailureException e) {
            if(query instanceof Not) {
                return new HashMap<>();
            }
            if(patternIterator.hasNext()) {
                return next();
            } else {
                return null;
            }
        }
    }
}
