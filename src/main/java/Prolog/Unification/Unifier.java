package Prolog.Unification;

import Prolog.*;

import java.util.HashMap;
import java.util.Map;

public class Unifier {

    Map<String, Term> vars = new HashMap<String, Term>();

    public void Unify(Term X, Term Y, PrologEnv env) {

    }

    public boolean isVarFree(String varName) {
        return !vars.containsKey(varName);
    }

    public void RegisterVar(String var, Term term) {

    }

}
