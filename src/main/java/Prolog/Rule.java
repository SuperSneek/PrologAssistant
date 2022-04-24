package Prolog;

import Prolog.Unification.UnificationClause;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

public class Rule extends Term {

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
    public UnificationClause[] unify(Term other, Unifier env) throws UnificationFailureException {
        return new UnificationClause[0];
    }
}
