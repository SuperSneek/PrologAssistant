package Prolog.Unification.UnificationClauses;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ClauseList extends UnificationClauseCarrier {

    private final List<UnificationClause> clauses;

    @Override
    public List<UnificationClause> next() {
        return null;
    }
}
