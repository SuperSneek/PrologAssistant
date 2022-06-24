package Prolog;

import Prolog.Unification.UnificationFailureException;

import java.util.HashMap;
import java.util.Map;

public class Rule extends PlPattern {

    Term left;
    Term right;



    public Rule(Term left, Term right) {
        this.left = left;
        this.right = right;
        name = left.name;
    }

    @Override
    public String toString() {
        return left.toString() + ":-" + right.toString();
    }

    @Override
    public Map<String, Term> unify(Term queryTerm, PrologEnv env,Map<String, Term> vars) throws UnificationFailureException {
        Map<String, Term> takenVars = left.unify(queryTerm, env, vars);
        Query answer = env.Query(right, takenVars);
        Map<String, Term> var = answer.next();
        if(var != null) {
            return var;
        } else {
            throw new UnificationFailureException();
        }
    }
}
