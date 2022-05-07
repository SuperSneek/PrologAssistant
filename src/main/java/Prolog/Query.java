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



    public Query(Term queryTerm, PrologEnv env) {
        //TODO: multithread
        patternIterator = env.findMatchingPatterns(queryTerm);
        this.env = env;
        this.query = queryTerm;
    }

    private Map<String, Term> unifyPattern() throws UnificationFailureException {
        //TODO: remove instanceof stupidity
        if(!patternIterator.hasNext()) {
            throw new UnificationFailureException();
        }
        PlPattern pattern = patternIterator.next();
        return (pattern.unify(query, env, new HashMap<String, Term>()));
    }

    @Override
    public boolean hasNext() {
        return patternIterator.hasNext();
    }

    @Override
    public Map<String, Term> next() {
        try {
            return unifyPattern();
        } catch (UnificationFailureException e) {
            if(patternIterator.hasNext()) {
                return next();
            } else {
                return null;
            }
        }
    }
}
