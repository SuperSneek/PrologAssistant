package Prolog.Unification.UnificationClauses;

import Prolog.PList;
import Prolog.Unification.Unifier;
import Prolog.Variable;
import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.List;

public class MultipleClausePossibilites extends UnificationClauseCarrier {

    Unifier env;
    PList rightList;
    PList leftList;

    Variable head;
    PList tail;

    //Initialized as null, for recursive list reexecution
    UnificationClauseCarrier current = null;

    @Override
    public boolean hasNext() {
        return true;
    }

    @SneakyThrows
    @Override
    public List<UnificationClause> next() {
        LinkedList<UnificationClause> out = new LinkedList<>();
        out.add((new UnificationClause(head, new PList(rightList.item, leftList))));
        new SingleClause(new UnificationClause(tail, rightList.next)));
    }

    private UnificationClauseCarrier generateNextClauses() {

    }

}
