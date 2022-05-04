package Prolog;

import Prolog.Unification.UnificationClause;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PList extends Term{

    protected Term next;
    protected Term item;

    public PList(Term term, Term next) {
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
    public List<UnificationClause> unify(Term other, Unifier env) throws UnificationFailureException {
        if(!(other instanceof PList otherL)) {
            throw new UnificationFailureException();
        } else {
            List<UnificationClause> clauses = item.unify(otherL.item, env);
            if(next != null && otherL.next != null) {
                clauses.addAll(next.unify(otherL.next, env));
            }
            return clauses;
        }
    }

}
