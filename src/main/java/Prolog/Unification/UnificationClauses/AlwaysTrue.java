package Prolog.Unification.UnificationClauses;

import java.util.LinkedList;
import java.util.List;

public class AlwaysTrue extends UnificationClauseCarrier {

    private boolean returned;

    @Override
    public boolean hasNext() {
        return !returned;
    }

    @Override
    public List<UnificationClause> next() {
        returned = true;
        return new LinkedList<>();
    }
}
