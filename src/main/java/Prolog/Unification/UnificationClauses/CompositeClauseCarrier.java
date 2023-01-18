package Prolog.Unification.UnificationClauses;

import Prolog.Unification.UnificationFailureException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CompositeClauseCarrier extends UnificationClauseCarrier {

    private final List<UnificationClauseCarrier> carriers;
    private final BufferedSolution bufferedSolutions;

    public CompositeClauseCarrier(List<UnificationClauseCarrier> carriers) throws UnificationFailureException {
        if(carriers == null || carriers.size() == 0) {
            throw new UnificationFailureException();
        }
        this.carriers = carriers;
        bufferedSolutions = new BufferedSolution(carriers.size());
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
            if(carrier.hasNext() && (!hasChanged || bufferedSolutions.getList(i) == null)) {
                hasChanged = true;
                bufferedSolutions.setList(i, carrier.next());
            }
            out.addAll(bufferedSolutions.getList(i));
        }
        if(!hasChanged) {
            return null;
        } else {
            return List.copyOf(out);
        }

    }

}
