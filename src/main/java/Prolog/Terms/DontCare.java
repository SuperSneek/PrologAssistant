package Prolog.Terms;

import Prolog.Unification.UnificationClauses.AlwaysTrue;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;

public class DontCare extends Variable{

    public DontCare() {
        super ("_");
    }

    @Override
    public UnificationClauseCarrier generateClauses(Term other) {
        return new AlwaysTrue();
    }

}
