package Prolog;

import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Query implements Iterator<Map<String, Term>> {

    private final Iterator<PlPattern> patternIterator;
    private final PrologEnv env;
    private final Term query;
    private final Map<String, Term> vars;


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
        this.query = queryTerm;
        this.vars = vars;
    }

    private Map<String, Term> unifyPattern() throws UnificationFailureException {
        //TODO: remove instanceof stupidity
        if(!patternIterator.hasNext()) {
            throw new UnificationFailureException();
        }
        PlPattern pattern = patternIterator.next();
        return (pattern.unify(query, env, vars));
    }

    @Override
    public boolean hasNext() {
        return patternIterator.hasNext();
    }

    @Override
    public Map<String, Term> next() {
        try {
            Map<String, Term> result = unifyPattern();
            if(result == null) {
                throw new UnificationFailureException();
            }
            return result;
        } catch (UnificationFailureException e) {
            if(patternIterator.hasNext()) {
                return next();
            } else {
                return null;
            }
        }
    }
}
