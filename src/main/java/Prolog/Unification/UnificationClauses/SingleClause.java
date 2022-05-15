package Prolog.Unification.UnificationClauses;

import lombok.AllArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class SingleClause extends UnificationClauseCarrier {

    private final UnificationClause clause;

    @Override
    public List<UnificationClause> next() {
        return List.of(clause);
    }
}
