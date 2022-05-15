package Prolog.Unification.UnificationClauses;

import Prolog.PList;
import Prolog.Unification.Unifier;
import Prolog.Variable;
import lombok.SneakyThrows;

import java.util.List;

public class MultipleClausePossibilites extends UnificationClauseCarrier {

    Unifier env;
    PList rightList;
    PList leftList;

    Variable head;
    PList tail;

    @Override
    public boolean hasNext() {
        return true;
    }

    @SneakyThrows
    @Override
    public List<UnificationClause> next() {
        return rightList.generateClauses(tail);
    }

}
