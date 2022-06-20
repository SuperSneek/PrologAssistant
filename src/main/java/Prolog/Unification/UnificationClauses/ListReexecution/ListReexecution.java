package Prolog.Unification.UnificationClauses.ListReexecution;

import Prolog.PList;
import Prolog.Term;
import Prolog.Unification.UnificationClauses.ClauseList;
import Prolog.Unification.UnificationClauses.CompositeClauseCarrier;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;
import Prolog.Variable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jonas lewandrowski
 * This class takes two lists and creates an iterator which produces ALL the different permutations of variables that
 * might unify those two lists
 */
public class ListReexecution extends UnificationClauseCarrier {


    /**
     * This contains the "domains" in which unification occurs - basically parts of the two lists
     * that are delimited by common (immutable) elements
     */
    private List<UnificationClauseCarrier> domainList = new ArrayList<>();
    private CompositeClauseCarrier current;

    private PList leftRemaining;
    private PList rightRemaining;

    public ListReexecution(PList left, PList right) throws UnificationFailureException {
        leftRemaining = left;
        rightRemaining = right;
        parse();
        clean();
        current = new CompositeClauseCarrier(domainList);
    }

    private void clean() {
        if(domainList.size() <= 1) {
            return;
        }
        //Code is inefficient but only executed at startup
        List<UnificationClauseCarrier> newDomain = new LinkedList<>();
        boolean done = false;
        for (int i = 0; i < domainList.size() - 1; i++) {
            if(domainList.get(i) instanceof VariableDomain & domainList.get(i+1) instanceof VariableDomain) {
                VariableDomain a = (VariableDomain) domainList.get(i);
                VariableDomain b = (VariableDomain) domainList.get(i+1);
                newDomain.add(a.merge(b));
                break;
            }
            if(i == domainList.size() - 1) {
                done = true;
            }
        }
        domainList = newDomain;
        if(!done) {
            clean();
        }
    }

    public void parse() throws UnificationFailureException {
        while(!leftRemaining.isEmpty() || !rightRemaining.isEmpty()) {
            if(leftRemaining.item instanceof Variable var) {
                leftRemaining = leftRemaining.next;
                rightRemaining = parseVariables(rightRemaining, leftRemaining.item, var);
            } else if(rightRemaining.item instanceof Variable var) {
                rightRemaining = rightRemaining.next;
                leftRemaining = parseVariables(leftRemaining, rightRemaining.item, var);
            }else {
                parseOtherTerms();
            }
        }
        if(!leftRemaining.isEmpty() || !rightRemaining.isEmpty()) {
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
        while(!termList.isEmpty()) {
            if(termList.item.equals(listEnd)) {
                break;
            }
            list = new PList(termList.item, list);
            termList = termList.next;
        }
        domainList.add(new VariableDomain(List.of(variable), list.reverse()));
        return termList;
    }

    private void parseOtherTerms() {
        List<UnificationClause> clauses = new ArrayList<>();
        while(!leftRemaining.isEmpty() && !rightRemaining.isEmpty()
                && !(leftRemaining.item instanceof Variable) &&
                !(rightRemaining.item instanceof Variable)) {
            clauses.add(new UnificationClause(leftRemaining.item, rightRemaining.item));
            leftRemaining = leftRemaining.next;
            rightRemaining = rightRemaining.next;
        }
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
