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
import java.util.LinkedList;
import java.util.List;

/**
 * @author jonas lewandrowski
 * This class takes two lists and creates an iterator which produces ALL the different permutations of variables that
 * might unify those two lists
 */
public class ListReexecution {


    /**
     * This contains the "domains" in which unification occurs - basically parts of the two lists
     * that are delimited by common (immutable) elements
     */
    private List<UnificationClauseCarrier> domainList = new ArrayList<>();
    private CompositeClauseCarrier current;

    private PList leftRemaining;
    private PList rightRemaining;

    public UnificationClauseCarrier generateCarrier(PList left, PList right) throws UnificationFailureException {
        leftRemaining = left;
        rightRemaining = right;
        parse();
        clean();
        if(domainList.size() == 1) {
            return domainList.get(0);
        }
        return new CompositeClauseCarrier(domainList);
    }

    private void clean() {
        if(domainList.size() <= 1) {
            return;
        }
        //Code is inefficient but only executed at startup
        List<UnificationClauseCarrier> newDomain = new LinkedList<>();
        boolean done = false;
        for (int i = 0; i < domainList.size(); i++) {
            if(i < domainList.size() - 1 && domainList.get(i) instanceof VariableDomain & domainList.get(i+1) instanceof VariableDomain) {
                VariableDomain a = (VariableDomain) domainList.get(i);
                VariableDomain b = (VariableDomain) domainList.get(i+1);
                newDomain.add(a.merge(b));
                if(i+1< domainList.size()) {
                    newDomain.addAll(domainList.subList(i+2, domainList.size()));
                }
                break;
            } else {
                newDomain.add(domainList.get(i));
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
            if(leftRemaining.getItem() instanceof Variable var) {
                leftRemaining = leftRemaining.next;
                rightRemaining = parseVariables(rightRemaining, leftRemaining.getItem(), var);
            } else if(rightRemaining.getItem() instanceof Variable var) {
                rightRemaining = rightRemaining.next;
                leftRemaining = parseVariables(leftRemaining, rightRemaining.getItem(), var);
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
            if(termList.getItem().equals(listEnd)) {
                break;
            }
            list = new PList(termList.getItem(), list);
            termList = termList.next;
        }
        domainList.add(new VariableDomain(List.of(variable), list.reverse()));
        return termList;
    }

    private void parseOtherTerms() {
        List<UnificationClause> clauses = new ArrayList<>();
        while(!leftRemaining.isEmpty() && !rightRemaining.isEmpty()
                && !(leftRemaining.getItem() instanceof Variable) &&
                !(rightRemaining.getItem() instanceof Variable)) {
            clauses.add(new UnificationClause(leftRemaining.getItem(), rightRemaining.getItem()));
            leftRemaining = leftRemaining.next;
            rightRemaining = rightRemaining.next;
        }
        domainList.add(new ClauseList(clauses));
    }
}
