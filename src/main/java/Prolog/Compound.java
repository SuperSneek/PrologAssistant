package Prolog;

import Prolog.Unification.UnificationClauses.CompositeClauseCarrier;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;

import java.util.ArrayList;

public class Compound extends Term {

    public PList values;
    private final int length;

    public Compound(String name, Term[] values) {
        this.name = name;
        this.values = new PList(null);
        length = values.length;
        for(Term t : values) {
            this.values.setNext(new PList(t));
        }
    }

    @Override
    public String toString() {
        return name + "(" + (values) + ")";
    }

    @Override
    public UnificationClauseCarrier generateClauses(Term other) throws UnificationFailureException {
        if(other instanceof Compound otherC) {
            if(((Compound) other).length == length) {
                ArrayList<UnificationClauseCarrier> out = new ArrayList<>();
                PList a = values;
                PList b = otherC.values;
                while(!a.isEmpty() && !b.isEmpty()){
                    out.add((a.getItem().generateClauses(b.getItem())));
                    a = a.next;
                    b = b.next;
                }
                return new CompositeClauseCarrier(out);
            }
        }
        throw new UnificationFailureException();
    }

    @Override
    public boolean equals(Term other) {
        boolean out = true;
        if(other instanceof Compound a) {
            if(!a.name.equals(name)) {
                return false;
            }
            if(a.length != length) {
                return false;
            }
            return values.equals(a.values);
        }
        return out;
    }
    
    @Override
    public boolean contains(Term c) {
        for (PList it = values; !it.isEmpty(); ) {
            Term t = it.getItem(); it = it.next;
            if(t.equals(c)) {
                return true;
            }
        }
        return false;
    }
}
