package Prolog.Unification.UnificationClauses.ListReexecution;

import Prolog.PList;
import Prolog.Unification.UnificationClauses.SingleClause;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Variable;
import lombok.AllArgsConstructor;

import java.util.List;

public class VariableDomain extends UnificationClauseCarrier {

    public VariableDomain(Variable variable, PList list) {
        this.variable = variable;
        this.list = list;
        listLength = list.length();
    }

    private PList list;

    private final Variable variable;

    int partLength = 0;

    private int listLength = 0;

    @Override
    public boolean hasNext() {
        return true;
    }


    public void expand(PList list, int length) {
        listLength += length;
        this.list = list.concat(list);
    }

    public PList shrink(int n) {
        listLength -= n;
        PList out = list.drop(listLength);
        this.list = list.take(listLength);
        return out;
    }

    @Override
    public List<UnificationClause> next() {
        return new SingleClause(list, variable).next();
    }
}
