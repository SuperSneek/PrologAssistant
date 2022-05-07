package Prolog;

import Prolog.Unification.UnificationClause;
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
    public Iterator<List<UnificationClause>> generateClauses(Term other, Unifier env) throws UnificationFailureException {
        if(!(other instanceof PList otherL)) {
            throw new UnificationFailureException();
        } else {
                return new Iterator<List<UnificationClause>>() {

                    PList otherL;
                    Iterator<List<UnificationClause>> currentClauses;

                    @Override
                    public boolean hasNext() {
                        return otherL.next != null || currentClauses.hasNext();
                    }

                    @Override
                    public List<UnificationClause> next() {
                        if(!hasNext()) {
                            return null;
                        }
                        try {
                            otherL.item
                            return item.generateClauses(otherL.item, env).next().addAll(next.generateClauses(otherL.next, env).next());
                        } catch (UnificationFailureException e) {
                            return null;
                        }
                    }
                };
        }
    }

}
