package Prolog.Unification;

import Prolog.Terms.Term;
import Prolog.Unification.UnificationClauses.UnificationClause;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Substitution {

    Term source;
    Term target;

    public List<UnificationClause> substitute(List<UnificationClause> clauses) {
        ArrayList<UnificationClause> out = new ArrayList<>();
        //TODO: Find out
        for (UnificationClause clause : clauses) {
            out.add(sub(clause));
        }
        return out;
    }

    private UnificationClause sub(UnificationClause clause) {
        Term left = clause.left;
        Term right = clause.right;
        if(clause.left.equals(source)) {
            left = target;
        }
        if(clause.right.equals(source)) {
            right = target;
        }
        return new UnificationClause(left, right);
    }

}
