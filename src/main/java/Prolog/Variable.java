package Prolog;


import Prolog.Unification.UnificationClauses.SingleClause;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

import java.util.ArrayList;
import java.util.List;

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
    public UnificationClauseCarrier generateClauses(Term other) throws UnificationFailureException {
        return new SingleClause(new UnificationClause(this, other));
    }
}
