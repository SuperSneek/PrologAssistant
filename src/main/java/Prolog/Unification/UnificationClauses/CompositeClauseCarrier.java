package Prolog.Unification.UnificationClauses;

import Prolog.Unification.UnificationFailureException;

import java.util.ArrayList;
import java.util.List;

public class CompositeClauseCarrier extends UnificationClauseCarrier {

    private final List<UnificationClauseCarrier> carriers;
    private final List[] bufferedSolutions;

    public CompositeClauseCarrier(List<UnificationClauseCarrier> carriers) throws UnificationFailureException {
        this.carriers = carriers;
        bufferedSolutions = new List[carriers.size()];
    }

    public boolean hasNext() {
        boolean changed;
        for (int i = 0; i < carriers.size(); i++) {
            if(carriers.get(i).hasNext()) {
                return true;
            }
        }
        return false;
    }

    public List<UnificationClause> next() {
        List<UnificationClause> out = new ArrayList<>();
        boolean hasChanged = false;
        for (int i = 0; i < carriers.size(); i++) {
            UnificationClauseCarrier carrier = carriers.get(i);
            if(carrier.hasNext() && (!hasChanged || bufferedSolutions[i] == null)) {
                hasChanged = true;
                bufferedSolutions[i] = carrier.next();
            }
            out.addAll(bufferedSolutions[i]);
        }
        if(!hasChanged) {
            return null;
        } else {
            return out;
        }

    }

}
