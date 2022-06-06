package Prolog.Unification.UnificationClauses;

import lombok.AllArgsConstructor;

import java.util.List;

public class ClauseList extends UnificationClauseCarrier {

    private final List<UnificationClause> clauses;

    public ClauseList(List<UnificationClause> clauses) {
        this.clauses = clauses;
    }
    private boolean returned = false;

    @Override
    public boolean hasNext() {
        return !returned;
    }

    @Override
    public List<UnificationClause> next() {
        return returned ? null : clauses;
    }
}
