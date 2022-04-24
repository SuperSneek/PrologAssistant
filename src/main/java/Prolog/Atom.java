package Prolog;

import Prolog.Unification.UnificationClause;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

public class Atom extends Term{

    public Atom(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public UnificationClause[] unify(Term other, Unifier env) throws UnificationFailureException {
        if(!(other instanceof Atom)) {
            throw new UnificationFailureException();
        } else if(other.getName() == getName()) {
            throw new UnificationFailureException();
        }
        return new UnificationClause[0];
    }
}
