package Prolog.Unification.UnificationClauses;

import Prolog.Term;
import lombok.AllArgsConstructor;

import java.util.LinkedList;
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
        return returned ? null : List.of(clause);
    }
}
