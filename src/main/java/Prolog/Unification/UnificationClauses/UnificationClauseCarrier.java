package Prolog.Unification.UnificationClauses;

import java.util.Iterator;
import java.util.List;

public abstract class UnificationClauseCarrier implements Iterator<List<UnificationClause>> {

    public boolean hasNext() {
        return true;
    }


}
