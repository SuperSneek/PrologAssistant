package Prolog;

import Prolog.Unification.UnificationClause;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
    public Map<String, Term> findSolution() {
        return right.findSolution();
    }
}
