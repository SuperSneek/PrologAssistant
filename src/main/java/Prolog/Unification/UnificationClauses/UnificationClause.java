package Prolog.Unification.UnificationClauses;

import Prolog.Terms.Term;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnificationClause {
    public Term left;
    public Term right;

    @Override
    public String toString() {
        return "(" + left.toString() + " , " + right.toString() + ")";
    }

}
