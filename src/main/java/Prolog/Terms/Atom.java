package Prolog.Terms;

import Prolog.Unification.UnificationClauses.SingleClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;

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
    public UnificationClauseCarrier generateClauses(Term other) throws UnificationFailureException {
        if(other instanceof Atom) {
            return new SingleClause(this, other);
        } else {
            throw new UnificationFailureException();
        }

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
        return new Atom(name);
    }
}
