package Prolog.Rule;

import Prolog.*;
import Prolog.Terms.PList;
import Prolog.Terms.Term;
import Prolog.Terms.Variable;
import Prolog.Unification.Substitution;
import Prolog.Unification.UnificationFailureException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Rule extends PlPattern implements Solution {

    Term left;
    Term[] right;
    Query answer;
    Map<String, Term> leftS = new HashMap<>();
    Solution leftSol;
    Solution[] rightSol;

    private final PrologEnv env;
    public Rule(Term left, PList right, PrologEnv env) {
        this.left = left;
        this.right = right.toArray();
        rightSol = new Solution[this.right.length];
        this.env = env;
        name = left.getName();
    }

    @Override
    public String toString() {
        return left.toString() + ":-" + Arrays.toString(right);
    }

    @Override
    public Solution unify(Term queryTerm, PrologEnv env, Map<String, Term> vars) throws UnificationFailureException {
        leftSol = left.unify(queryTerm, env, vars);
        leftS = leftSol.next();
        return this;
    }

    /**
     * @return wether there is another possibility for rule reexecution
     */
    @Override
    public boolean hasNext() {
        return leftSol.hasNext() || rightSolHasNext();
    }

    private boolean rightSolHasNext() {
        for (Solution s:
                rightSol) {
            if(s == null) {
                return true;
            }
            if(s.hasNext()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Term> next() {
        //Find inital variable
        Map<String, Term> out = leftS;
        int index = 0;
        try {
            try {
                return calculateNextRecursion(out, index);
            } catch (UnificationFailureException e) {
                return calculateNextRecursion(out, index);
            }
        } catch (UnificationFailureException e) {
            if(leftSol.hasNext()) {
                leftSol.next();
                return next();
            }
        }

        return null;
    }

    private Map<String, Term> calculateNextRecursion(Map<String, Term> vars, int index) throws UnificationFailureException {
        if(index == right.length) {
            return vars;
        }
        if(rightSol[index] == null || !rightSol[index].hasNext()) {
            rightSol[index] = env.Query(right[index], vars);
        }
        Map<String, Term> result = rightSol[index].next();
        if(result == null) {
            throw new UnificationFailureException();
        }
        vars.putAll(result);
        try {
            return calculateNextRecursion(vars, index + 1);
        } catch (UnificationFailureException e) {
            for (String s:
                 result.keySet()) {
                vars.remove(s);
            }
            return calculateNextRecursion(vars, index);
        }
    }

    private Map<String, Term> reduced(Map<String, Term> toReduce, Map<String, Term> subs ) {
        for(String key : subs.keySet()) {
                for (String other:
                     toReduce.keySet()) {
                    if(other.equals(key)) {
                       // toReduce.put(subs.get())
                    }
                }
            Substitution sub = new Substitution(new Variable(key), subs.get(key));
        }
        return null;
    }

}
