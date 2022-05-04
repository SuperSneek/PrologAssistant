package Prolog;

import Prolog.Unification.UnificationClause;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.ArrayList;
import java.util.Arrays;
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
    public List<UnificationClause> unify(Term other, Unifier env) throws UnificationFailureException {
        if(other instanceof Compound otherC) {
            if(otherC.values.length == values.length) {
                ArrayList<UnificationClause> out = new ArrayList<UnificationClause>();
                for (int i = 0; i < values.length; i++) {
                    out.add(new UnificationClause(otherC.values[i], values[i]));
                }
                return out;
            }
        }
        throw new UnificationFailureException();
    }
}
