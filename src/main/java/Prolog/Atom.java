package Prolog;

import Prolog.Unification.UnificationClauses.SingleClause;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.Iterator;
import java.util.List;

public class Atom extends Term{

    public Atom(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public UnificationClauseCarrier generateClauses(Term other) {
        return new SingleClause(new UnificationClause(this, other));
    }

    @Override
    public boolean equals(Term other) {
        if(other instanceof Atom a) {
            return a.name.equals(name);
        }
        return false;
    }
}
