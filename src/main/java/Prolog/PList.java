package Prolog;

import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class PList extends Term{

    protected PList next;
    protected Term item;

    public PList(Term term, PList next) {
        this.name = "List";
        this.item = term;
        this.next = next;
    }

    public PList(Term term) {
        this.name = "List";
        this.item = term;
        this.next = null;
    }

    public static PList fromQueue(Stack<Term> terms) {
        if(terms.isEmpty()) {
            return new PList(null, null);
        }
        return new PList(terms.pop(), fromQueue(terms));
    }

    public Term head() {
        return item;
    }

    public Term tail() {
        return next;
    }

    @Override
    public String toString() {
        if(item == null) {
            return "";
        } else if(next == null) {
            return "[" + item.toString() + "]";
        }
        return "[" + item + next.toString() + "]";
    }

    @Override
    public UnificationClauseCarrier generateClauses(Term other) throws UnificationFailureException {
        if(!(other instanceof PList otherL)) {
            throw new UnificationFailureException();
        } else {
            if(otherL.item instanceof Variable) {

            }
        }
    }

}
