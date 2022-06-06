package Prolog.Unification.UnificationClauses;

import Prolog.Unification.UnificationFailureException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class CompositeClauseCarrier extends UnificationClauseCarrier {

    private final List<UnificationClauseCarrier> carriers;
    private final List<ClauseList> previousResults = new ArrayList<>();

    public CompositeClauseCarrier(List<UnificationClauseCarrier> carriers) throws UnificationFailureException {
        this.carriers = carriers;
        for (UnificationClauseCarrier item:
                carriers) {
            if(item.hasNext()) {
                previousResults.add(new ClauseList(item.next()));
            } else {
                throw new UnificationFailureException();
            }
        }
    }

    public boolean hasNext() {
        for (UnificationClauseCarrier item:
             carriers) {
            if(item.hasNext()) {
                return true;
            }
        }
        return false;
    }

    public List<UnificationClause> next() {
        List<UnificationClause> out = new ArrayList<>();
        boolean hasChanged = false;
        for (UnificationClauseCarrier item:
                carriers) {
            if(item.hasNext() && !hasChanged) {
                hasChanged = true;
                item.next();
            }
            out.addAll(item.next());
        }
        if(!hasChanged) {
            return null;
        } else {
            return out;
        }

    }

}
