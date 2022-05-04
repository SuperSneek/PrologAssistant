package Prolog;


import Prolog.Unification.UnificationClause;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Variable extends Term{


    PrologEnv env;

    public Variable(String name, PrologEnv env) {
        this.name = name;
        this.env = env;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public List<UnificationClause> unify(Term other, Unifier env) throws UnificationFailureException {
        ArrayList<UnificationClause> out = new ArrayList<UnificationClause>();
        out.add(new UnificationClause(this, other));
        return out;
    }
}
