package Prolog.Unification.UnificationClauses.ListReexecution;

import Prolog.PList;
import Prolog.Term;
import Prolog.Unification.UnificationClauses.ClauseList;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;
import Prolog.Variable;

import java.util.ArrayList;
import java.util.List;

public class ListReexecution extends UnificationClauseCarrier {

    List<UnificationClauseCarrier> domainList;

    private PList leftRemaining;
    private PList rightRemaining;

    public ListReexecution(PList left, PList right) throws UnificationFailureException {
        parse();
    }

    public void parse() throws UnificationFailureException {
        while(leftRemaining.hasNext() && rightRemaining.hasNext()) {
            if(leftRemaining.item instanceof Variable var) {
                leftRemaining = leftRemaining.next;
                rightRemaining = parseVariables(rightRemaining, leftRemaining.item, var);
            } else {
                parseOtherTerms();
            }
        }
        if(leftRemaining.hasNext() || rightRemaining.hasNext()) {
            throw new UnificationFailureException();
        }
    }

    /**
     *
     * @param termList
     * @param listEnd
     * @param variable
     * @return
     */
    private PList parseVariables(PList termList, Term listEnd, Variable variable) {
        PList list = new PList(null);
        while(termList.hasNext()) {
            list = new PList(termList.item, list);
            if(list.item.equals(listEnd)) {
                break;
            }
        }
        domainList.add(new VariableDomain(list, variable));
        return list;
    }

    private void parseOtherTerms() {
        List<UnificationClause> clauses = new ArrayList<>();
        while(leftRemaining.hasNext() && rightRemaining.hasNext()
                && !(leftRemaining.item instanceof Variable) &&
                !(rightRemaining.item instanceof Variable)) {
            clauses.add(new UnificationClause(leftRemaining.item, rightRemaining.item));
        }
        domainList.add(new ClauseList(clauses));
    }

    @Override
    public List<UnificationClause> next() {
        return null;
    }
}
