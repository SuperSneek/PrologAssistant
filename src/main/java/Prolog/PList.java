package Prolog;

import Prolog.Unification.UnificationClauses.ListReexecution.ListReexecution;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class PList extends Term implements Iterator<PList> {

    public PList next;
    public Term item;

    public PList(Term term, PList next) {
        this.name = "List";
        this.item = term;
        this.next = next;
    }

    public PList(Term term) {
        this.name = "List";
        this.item = term;
        if(term != null) {
            this.next = new PList(null);
        }
    }

    public void setNext(PList next) {
        if(item == null) {
            item = next.item;
            this.next = next.next;
        } else {
            this.next = next;
        }
    }

    public static PList fromQueue(Stack<Term> terms) {
        if(terms.isEmpty()) {
            return new PList(null, null);
        }
        return new PList(terms.pop(), fromQueue(terms));
    }

    public boolean hasNext() {
        if(next == null) {
            return false;
        }
        return next.item != null;
    }

    @Override
    public PList next() {
        return next;
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
            return new ListReexecution(this, otherL);
        }
    }

    @Override
    public boolean equals(Term o) {
        if(o instanceof PList l) {
            boolean out = item.equals(l.item);
            if(hasNext()) {
                out &= next.equals(l.next);
            }
            return out;
        }
       return false;
    }

    public boolean isEmpty() {
        return item == null;
    }

    public PList reverse() {
        PList out = new PList(null);
        PList acc = this;
        while(!acc.isEmpty()) {
            out = new PList(acc.item, out);
            acc = acc.next;
        }
        return out;
    }

    @Override
    public boolean contains(Term c) {
        if(!hasNext()) {
            return false;
        }
        return item.equals(c) || next.contains(c);
    }

    public PList take(int n) {
        if(n > 0) {
            return new PList(item, next.take(n-1));
        }
        return new PList(null);
    }

    public PList drop(int n) {
        if(n == 0) {
            return this;
        }
        return next.drop(n-1);
    }

    public PList concat(PList other) {
        if(isEmpty()) {
            return other;
        }
        PList connection = this;
        while(connection.hasNext()) {
            connection = connection.next;
        }
        connection.next = other;
        return this;
    }

    public int length() {
        if(isEmpty()) {
            return 0;
        }
        return 1 + next.length();
    }
}
