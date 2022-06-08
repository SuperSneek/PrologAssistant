package Prolog.Unification.UnificationClauses.ListReexecution;

import Prolog.PList;
import Prolog.Unification.UnificationClauses.SingleClause;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Variable;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class VariableDomain extends UnificationClauseCarrier {

    private final PList list;

    private final Variable variable;

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public List<UnificationClause> next() {
        return new SingleClause(list, variable).next();
    }
}
