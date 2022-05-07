package Prolog;

import Prolog.Unification.UnificationClause;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Atom extends Term{

    public Atom(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
<<<<<<< HEAD
    public List<UnificationClause> generateClauses(Term other, Unifier env) throws UnificationFailureException {
=======
    public List<UnificationClause> unify(Term other, Unifier env) throws UnificationFailureException {
>>>>>>> origin/master
        if(!(other instanceof Atom)) {
            throw new UnificationFailureException();
        } else if(!other.getName().equals(getName())) {
            throw new UnificationFailureException();
        }
        return new ArrayList<>();
    }
}
