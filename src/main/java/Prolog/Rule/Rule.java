package Prolog.Rule;

import Prolog.*;
import Prolog.Terms.PList;
import Prolog.Terms.Term;
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

    int index = 0;

    private final PrologEnv env;
    public Rule(Term left, PList right, PrologEnv env) {
        this.left = left;
        this.right = right.toArray();
        rightSol = new Solution[this.right.length];
        for (int i = 0; i < rightSol.length; i++) {
            rightSol[i] = new EmptySolution();
        }
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
        GenerateNode(leftS);
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
            if(s instanceof EmptySolution) {
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
        try {
            return calculateNextRecursion(out);
        } catch (UnificationFailureException e) {
            if(leftSol.hasNext()) {
                index = 0;
                leftSol.next();
                return next();
            }
        }

        return null;
    }

    private Map<String, Term> calculateNextRecursion(Map<String, Term> vars) throws UnificationFailureException {


        Map<String, Term> newVars = (DescendLayer(vars));
        if(index == right.length) {
            AscendLayer();
            return newVars;
        } else {
            GenerateNode(newVars);
            try {
                return calculateNextRecursion(newVars);
            } catch (UnificationFailureException e) {
                AscendLayer();
                return calculateNextRecursion(vars);
            }
        }


        //if(rightSol[index] == null || !rightSol[index].hasNext()) {
        //    rightSol[index] = env.Query(right[index], vars);
        //}
        //Map<String, Term> result = rightSol[index].next();
        //if(result == null) {
        //    index--;
        //    throw new UnificationFailureException();
        //} else {
        //    index++;
        //}
        //vars.putAll(result);
        //try {
        //    index++;
        //    return calculateNextRecursion(vars);
        //} catch (UnificationFailureException e) {
        //    for (String s:
        //         result.keySet()) {
        //        vars.remove(s);
        //    }
        //    return calculateNextRecursion(vars);
        //}
    }

    /**
     * This method is used to generate a new node in the depth search algorithm
     * @throws UnificationFailureException a node cannot be generated with the present variables
     */
    private void GenerateNode(Map<String, Term> vars) throws UnificationFailureException {
        rightSol[index] = env.Query(right[index], vars);

    }

    /**
     * Should no solution be found in the current branch of our solution tree depth search
     * we can ascend back and try to find a new solution
     */
    private void AscendLayer() {
        index--;
    }

    /**
     * When a new solution has been gereated from a node, we can simply descend without any extra steps
     */
    private Map<String, Term> DescendLayer(Map<String, Term> vars) throws UnificationFailureException {
        Map<String, Term> newVars = rightSol[index].next();
        if(newVars == null) {
            throw new UnificationFailureException();
        }
        newVars.putAll(vars);
        index++;
        return newVars;
    }



}
