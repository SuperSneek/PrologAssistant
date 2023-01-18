package Prolog.Terms;

import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Unification.UnificationFailureException;

public class ClauseGenerator {

    private ClauseGenerator() throws IllegalAccessException {
        throw new IllegalAccessException("This is a utility class!");
    }

    public static UnificationClauseCarrier generateClauses(Term a, Term b) throws UnificationFailureException {
        try {
            return a.generateClauses(b);
        } catch (UnificationFailureException e) {
            return b.generateClauses(a);
        }
    }
}
