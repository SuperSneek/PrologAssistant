package Prolog.Terms;

import Prolog.Unification.UnificationClauses.ListReexecution.ListReexecution;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;

import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class PList extends Term implements Iterator<PList> {

    public PList next;
    private Term item;

    public PList(Term term, PList next) {
        this.name = "List";
        this.setItem(term);
        this.next = next;
    }

    public PList(Term term) {
        this.name = "List";
        this.setItem(term);
        if(term != null) {
            this.next = new PList(null);
        }
    }

    public void setItem(Term item) {
        if(item instanceof PList p) {
            if(p.isEmpty()) {
                return;
            }
        }
        this.item = item;
    }

    public void setNext(PList next) {
        if(next.getItem() == null && isEmpty()) {
            return;
        }
        if(getItem() == null) {
            setItem(next.getItem());
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
        return next.getItem() != null;
    }

    @Override
    public PList next() {
        return next;
    }

    public Term head() {
        return getItem();
    }

    public Term tail() {
        return next;
    }

    @Override
    public String toString() {
        if(getItem() == null) {
            return "";
        } else if(next == null) {
            return "[" + getItem().toString() + "]";
        }
        return "[" + getItem() + next.toString() + "]";
    }

    @Override
    public UnificationClauseCarrier generateClauses(Term other) throws UnificationFailureException {
        if(!(other instanceof PList otherL)) {
            throw new UnificationFailureException();
        } else {
            return (new ListReexecution().generateCarrier(this, otherL));
        }
    }

    @Override
    public boolean equals(Term o) {
        if(o instanceof PList l) {
            if(getItem() == null) {
                return l.getItem() == null;
            }
            boolean out = getItem().equals(l.getItem());
            if(hasNext()) {
                out &= next.equals(l.next);
            }
            return out;
        }
       return false;
    }

    @Override
    public Term substitute(Map<String, Term> vars) {
        PList p = this;
        while(!p.isEmpty()) {
            p.setItem(p.getItem().substitute(vars));
            p = p.next;
        }
        return this;
    }

    public boolean isEmpty() {
        return getItem() == null;
    }

    public PList reverse() {
        PList out = new PList(null);
        PList acc = this;
        while(!acc.isEmpty()) {
            out = new PList(acc.getItem(), out);
            acc = acc.next;
        }
        return out;
    }

    @Override
    public boolean contains(Term c) {
        if(!hasNext()) {
            return false;
        }
        return getItem().equals(c) || next.contains(c);
    }

    public PList take(int n) {
        if(n > 0) {
            return new PList(getItem(), next.take(n-1));
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

    public Term getItem() {
        return item;
    }

    public Term[] toArray() {
        Term[] out = new Term[length()];
        if(isEmpty()) {
            return out;
        }
        PList connection = this;
        for (int j = 0; j < out.length; j++) {
            out[j] = connection.item;
            connection = connection.next();
        }
        return out;
    }
}
