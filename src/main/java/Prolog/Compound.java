package Prolog;

import Prolog.Unification.UnificationClause;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.Arrays;

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
    public UnificationClause[] unify(Term other, Unifier env) throws UnificationFailureException {
        if(other instanceof Compound) {
            Compound otherC = (Compound) other;
            if(otherC.values.length == values.length) {
                UnificationClause[] clauses = new UnificationClause[values.length];
                for (int i = 0; i < values.length; i++) {
                    clauses[i] = new UnificationClause(otherC.values[i], values[i]);
                }
            }
        }
        throw new UnificationFailureException();
    }
}
