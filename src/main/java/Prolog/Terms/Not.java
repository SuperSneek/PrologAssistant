package Prolog.Terms;

import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;

import java.util.Map;

public class Not extends Term {

    private Term subject;

    public Not(Term subject) {
        this.subject = subject;
        this.name = subject.getName();
    }

    @Override
    public String toString() {
        return subject.toString();
    }

    @Override
    public UnificationClauseCarrier generateClauses(Term other) throws UnificationFailureException {
        return subject.generateClauses(other);
    }

    @Override
    public boolean equals(Term other) {
        return other instanceof Not n && n.subject.equals(subject) || subject.equals(other);
    }

    @Override
    public Term substitute(Map<String, Term> vars) {
        subject = subject.substitute(vars);
        return this;
    }
}
