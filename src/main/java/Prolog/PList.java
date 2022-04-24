package Prolog;

import Prolog.Unification.UnificationClause;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.Stack;

public class PList extends Term{

    protected PList next;
    protected Term item;

    public PList(String name, Term term, PList next) {
        this.name = name;
        this.item = term;
        this.next = next;
    }

    public PList(String name, Term term) {
        this.name = name;
        this.item = term;
        this.next = null;
    }

    public static PList fromQueue(Stack<Term> terms) {
        if(terms.isEmpty()) {
            return new PList("", null);
        }
        return new PList("", terms.pop(), fromQueue(terms));
    }

    public Term head() {
        return item;
    }

    public Term tail() {
        return next;
    }

    @Override
    public String toString() {
        if(item == null || next == null) {
            return "";
        }
        return item.toString() + next.toString();
    }

    @Override
    public UnificationClause[] unify(Term other, Unifier env) throws UnificationFailureException {
        if(!(other instanceof PList)) {
            throw new UnificationFailureException();
        } else {
            PList otherL = (PList) other;

            //TODO: ALso unify rest of list
            next.unify(otherL.next, env);

            return item.unify(otherL.item, env);
        }
    }

}
