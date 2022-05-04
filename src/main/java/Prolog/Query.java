package Prolog;

import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Query implements Iterator<Map<String, Term>> {

    private Iterator<PlPattern> patternIterator;
    private PrologEnv env;
    private Term query;



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
        Map<String, Term> vars = new HashMap<String, Term>();
        Unifier u = new Unifier();
        if(pattern instanceof Rule) {
            Query answer = env.Query(((Rule) pattern).right);
            Map<String, Term> var = answer.next();
            if(var != null) {
                vars.putAll(var);
            } else {
                throw new UnificationFailureException();
            }
        }
        return u.Unify(query, (Term) pattern, vars);
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
