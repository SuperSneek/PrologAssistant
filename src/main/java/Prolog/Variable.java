package Prolog;


import Prolog.Unification.UnificationClause;
import Prolog.Unification.UnificationFailureException;
import Prolog.Unification.Unifier;

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
    public UnificationClause[] unify(Term other, Unifier env) throws UnificationFailureException {
        if(env.isVarFree(name)) {
            env.RegisterVar(name, other);
            return new UnificationClause[0];
        }
        throw new UnificationFailureException();
    }
}
