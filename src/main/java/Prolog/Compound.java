package Prolog;

import Prolog.Unification.UnificationClauses.ClauseList;
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
                ArrayList<UnificationClause> out = new ArrayList<UnificationClause>();
                for (int i = 0; i < values.length; i++) {
                    out.add(new UnificationClause(otherC.values[i], values[i]));
                }
                return new ClauseList(out);
            }
        }
        throw new UnificationFailureException();
    }
}
