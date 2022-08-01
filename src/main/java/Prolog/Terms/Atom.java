package Prolog.Terms;

import Prolog.Unification.UnificationClauses.SingleClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;

import java.util.Map;

public class Atom extends Term {

    public Atom(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public UnificationClauseCarrier generateClauses(Term other) {
        return new SingleClause(this, other);
    }

    @Override
    public boolean equals(Term other) {
        if(other instanceof Atom a) {
            return a.name.equals(name);
        }
        return false;
    }

    @Override
    public Term substitute(Map<String, Term> vars) {
        return this;
    }
}
