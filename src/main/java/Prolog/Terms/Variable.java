package Prolog.Terms;


import Prolog.Unification.UnificationClauses.SingleClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;

import java.util.Map;

public class Variable extends Term {



    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public UnificationClauseCarrier generateClauses(Term other) throws UnificationFailureException {
        return new SingleClause(this, other);
    }

    @Override
    public boolean matches(Term other) {
        return true;
    }

    @Override
    public boolean equals(Term o) {
        if(o instanceof Variable l) {
            return l.name.equals(name) && !name.equals("_");
        }
        return false;
    }

    @Override
    public Term substitute(Map<String, Term> vars) {
        Term t = vars.get(name);
        if(t == null) {
            return this;
        } else {
            return t;
        }
    }
}
