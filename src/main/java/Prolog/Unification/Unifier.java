package Prolog.Unification;

import Prolog.*;
import Prolog.Unification.UnificationClauses.ClauseList;
import Prolog.Unification.UnificationClauses.CompositeClauseCarrier;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;

import java.util.*;

public class Unifier implements Iterator<Map<String, Term>> {

    List<String> vars = new LinkedList<>();

    private UnificationClauseCarrier carrier;

    private boolean resultCurrent = false;

    Map<String, Term> result = new HashMap<>();

    public Unifier(Term X, Term Y, Map<String, Term> vars) throws UnificationFailureException {
        try {
            carrier = X.generateClauses(Y);
        } catch (UnificationFailureException e) {
            carrier = Y.generateClauses(X);
        }
    }

    public Map<String, Term> RecursiveUnify(List<UnificationClause> clauses) throws UnificationFailureException {
        if(clauses.size() == 0) {
            return new HashMap<>();
        }
        UnificationClause head = clauses.get(0);
        List<UnificationClause> tail = clauses.subList(1,clauses.size());
        //Splitting functors into multiple clauses is already done in line Unify(...)
        if(head.left.equals(head.right)) {
           return RecursiveUnify(tail);
        } else if(head.left instanceof Variable var && !vars.contains(head.left.getName())) {
            vars.add(head.left.getName());
            if(head.right.contains(var)) {
                throw new UnificationFailureException();
            }
            Substitution sub = new Substitution(head.left, head.right);
            Map<String, Term> out = RecursiveUnify(sub.substitute(tail));
            out.put(head.left.getName(), head.right);
            return out;
        }
        else if(head.right instanceof Variable var && !vars.contains(head.right.getName())) {
            vars.add(head.right.getName());
            if(head.left.contains(var)) {
                throw new UnificationFailureException();
            }
            Substitution sub = new Substitution(head.right, head.left);
            Map<String, Term> out = RecursiveUnify(sub.substitute(tail));
            out.put(head.right.getName(), head.left);
            return out;
        } else {
            carrier = new CompositeClauseCarrier(List.of(head.right.generateClauses(head.left),
                    new ClauseList(tail)));
            return next();
        }
    }

    @Override
    public boolean hasNext() {
        if(!carrier.hasNext()) {
            return false;
        }
        try {
            result = RecursiveUnify(carrier.next());
            resultCurrent = true;
            return true;
        } catch (UnificationFailureException e) {
            return false;
        }
    }

    @Override
    public Map<String, Term> next() {
        if(resultCurrent) {
            resultCurrent = false;
            return result;
        }
        if(!carrier.hasNext()) {
            return null;
        }
        try {
            return RecursiveUnify(carrier.next());
        } catch (UnificationFailureException e) {
            return null;
        }
    }
}
