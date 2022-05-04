package Prolog.Unification;

import Prolog.Term;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Substitution {

    Term source;
    Term target;

    public List<UnificationClause> substitute(List<UnificationClause> clauses) {
        return clauses.stream().map(this::sub).toList();
    }

    private UnificationClause sub(UnificationClause clause) {
        if(clause.left.equals(source)) {
            clause.left = target;
        }
        if(clause.right.equals(source)) {
            clause.right = target;
        }
        return clause;
    }

}
