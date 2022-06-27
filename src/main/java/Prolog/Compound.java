package Prolog;

import Prolog.Unification.UnificationClauses.ClauseList;
import Prolog.Unification.UnificationClauses.CompositeClauseCarrier;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Compound extends Term {

    public Term[] values;

    public Compound(String name, Term[] values) {
        this.name = name;
        this.values = values;
    }

    @Override
    public String toString() {
        return name + "(" + Arrays.toString(values) + ")";
    }

    @Override
    public UnificationClauseCarrier generateClauses(Term other) throws UnificationFailureException {
        if(other instanceof Compound otherC) {
            if(otherC.values.length == values.length) {
                ArrayList<UnificationClauseCarrier> out = new ArrayList<>();
                for (int i = 0; i < values.length; i++) {
                    out.add((otherC.values[i].generateClauses(values[i])));
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
            out = a.name.equals(name);
            if(a.values.length != values.length) {
                return false;
            }
            for (int i = 0; i < values.length; i++) {
                if(!values[i].equals(a.values[i])) {
                    return false;
                }
            }
        }
        return out;
    }
    
    @Override
    public boolean contains(Term c) {
        for (Term t:
             values) {
            if(t.equals(c)) {
                return true;
            }
        }
        return false;
    }
}
