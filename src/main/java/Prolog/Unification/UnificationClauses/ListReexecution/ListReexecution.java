package Prolog.Unification.UnificationClauses.ListReexecution;

import Prolog.PList;
import Prolog.Term;
import Prolog.Unification.UnificationClauses.ClauseList;
import Prolog.Unification.UnificationClauses.CompositeClauseCarrier;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;
import Prolog.Variable;

import java.util.ArrayList;
import java.util.List;

public class ListReexecution extends UnificationClauseCarrier {

    List<UnificationClauseCarrier> domainList = new ArrayList<>();
    private CompositeClauseCarrier current;

    private PList leftRemaining;
    private PList rightRemaining;

    public ListReexecution(PList left, PList right) throws UnificationFailureException {
        leftRemaining = left;
        rightRemaining = right;
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
        current = new CompositeClauseCarrier(domainList);
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
        do {
            list = new PList(termList.item, list);
            if(list.item.equals(listEnd)) {
                break;
            }
        } while ((termList.hasNext()));
        domainList.add(new VariableDomain(list, variable));
        return list;
    }

    private void parseOtherTerms() {
        List<UnificationClause> clauses = new ArrayList<>();
        do {
            clauses.add(new UnificationClause(leftRemaining.item, rightRemaining.item));
            leftRemaining = leftRemaining.next;
            rightRemaining = rightRemaining.next;
        } while(leftRemaining.hasNext() && rightRemaining.hasNext()
                && !(leftRemaining.item instanceof Variable) &&
                !(rightRemaining.item instanceof Variable));
        domainList.add(new ClauseList(clauses));
    }

    @Override
    public boolean hasNext() {
        return current.hasNext();
    }

    @Override
    public List<UnificationClause> next() {
        return current.next();
    }
}
