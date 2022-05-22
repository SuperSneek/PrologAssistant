package Prolog.Unification.UnificationClauses;

import Prolog.Term;
import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class SingleClause extends UnificationClauseCarrier {

    private final UnificationClause clause;

    public SingleClause(Term left, Term right) {
        clause = new UnificationClause(left, right);
    }

    @Override
    public List<UnificationClause> next() {
        return List.of(clause);
    }
}
