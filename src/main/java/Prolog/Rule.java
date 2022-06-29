package Prolog;

import Prolog.Unification.UnificationFailureException;

import java.util.HashMap;
import java.util.Map;

public class Rule extends PlPattern implements Solution {

    Term left;
    Term right;
    Query answer;
    Map<String, Term> currentVars = new HashMap<>();
    Solution leftSol;
    Solution rightSol;

    private final PrologEnv env;
    public Rule(Term left, Term right, PrologEnv env) {
        this.left = left;
        this.right = right;
        this.env = env;
        name = left.name;
    }

    @Override
    public String toString() {
        return left.toString() + ":-" + right.toString();
    }

    @Override
    public Solution unify(Term queryTerm, PrologEnv env, Map<String, Term> vars) throws UnificationFailureException {
        leftSol = left.unify(queryTerm, env, vars);
        rightSol = env.Query(right, leftSol.next());
        return this;
    }

    /**
     * @return wether there is another possibility for rule reexecution
     */
    @Override
    public boolean hasNext() {
        return leftSol.hasNext() || rightSol.hasNext();
    }

    @Override
    public Map<String, Term> next() {
        if(rightSol.hasNext()) {
            return rightSol.next();
        }
        if(leftSol.hasNext()) {
            Map<String, Term> currentVars = leftSol.next();
            try {
                rightSol = env.Query(right, currentVars);
            } catch (UnificationFailureException e) {
                return next();
            }
        }
        return null;
    }
}
