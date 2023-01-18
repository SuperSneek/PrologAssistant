package Prolog.Unification.UnificationClauses;

import Prolog.Terms.Term;

import java.util.ArrayList;
import java.util.List;

public class SingleClause extends UnificationClauseCarrier {

    private final UnificationClause clause;

    public SingleClause(Term left, Term right) {
        clause = new UnificationClause(left, right);
    }
    private boolean returned = false;

    @Override
    public boolean hasNext() {
        return !returned;
    }

    @Override
    public List<UnificationClause> next() {
        boolean temp = returned;
        returned = true;
        ArrayList<UnificationClause> out = new ArrayList<>();
        out.add(clause);
        return temp ? null :out;
    }
}
