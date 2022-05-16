package Prolog.Unification;

import Prolog.*;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;

import java.util.*;

public class Unifier implements Iterator<Map<String, Term>> {

    Set<String> vars = new HashSet<>();

    private final UnificationClauseCarrier carrier;

    public Unifier(Term X, Term Y, Map<String, Term> vars) throws UnificationFailureException {
        carrier = X.generateClauses(Y);
    }

    private Map<String, Term> RecursiveUnify(List<UnificationClause> clauses) throws UnificationFailureException {
        if(clauses.size() == 0) {
            return new HashMap<>();
        }
        UnificationClause head = clauses.get(0);
        List<UnificationClause> tail = clauses.subList(1,clauses.size());
        //Splitting functors into multiple clauses is already done in line Unify(...)
        if(head.left.equals(head.right)) {
            RecursiveUnify(tail);
        } else if(head.left instanceof Variable && !vars.contains(head.left.getName())) {
            vars.add(head.left.getName());
            Substitution sub = new Substitution(head.left, head.right);
            Map<String, Term> out = RecursiveUnify(sub.substitute(tail));
            out.put(head.left.getName(), head.right);
            return out;
        }
        else if(head.right instanceof Variable && !vars.contains(head.right.getName())) {
            vars.add(head.right.getName());
            Substitution sub = new Substitution(head.right, head.left);
            Map<String, Term> out = RecursiveUnify(sub.substitute(tail));
            out.put(head.right.getName(), head.left);
            return out;
        }
        throw new UnificationFailureException();
    }

    @Override
    public boolean hasNext() {
        return carrier.hasNext();
    }

    @Override
    public Map<String, Term> next() {
        try {
            return RecursiveUnify(carrier.next());
        } catch (UnificationFailureException e) {
            throw new RuntimeException(e);
        }
    }
}
