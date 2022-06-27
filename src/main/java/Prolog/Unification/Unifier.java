package Prolog.Unification;

import Prolog.*;
import Prolog.Unification.UnificationClauses.ClauseList;
import Prolog.Unification.UnificationClauses.CompositeClauseCarrier;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;

import java.util.*;

public class Unifier implements Iterator<Map<String, Term>> {

    private final List<Substitution> subs;

    private final List<String> externalvars = new ArrayList<>();

    private UnificationClauseCarrier carrier;

    private boolean resultCurrent = false;

    Map<String, Term> result = new HashMap<>();

    public Unifier(Term X, Term Y, Map<String, Term> vars) throws UnificationFailureException {
        subs = createExternalVars(vars);
        externalvars.addAll(vars.keySet());
        try {
            carrier = X.generateClauses(Y);
        } catch (UnificationFailureException e) {
            carrier = Y.generateClauses(X);
        }
    }

    private List<Substitution> createExternalVars(Map<String, Term> vars) {
        List<Substitution> subs = new LinkedList<>();
        for(String item : vars.keySet()) {
            Substitution sub = new Substitution(new Variable(item), vars.get(item));
            subs.add(sub);
        }
        return subs;
    }

    public Map<String, Term> RecursiveUnify(List<UnificationClause> clauses) throws UnificationFailureException {
        //Apply external vars
        for(Substitution sub : subs) {
            clauses = sub.substitute(clauses);
        }
        List<String> vars = new LinkedList<>(externalvars);
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
            throw new UnificationFailureException();
           // try {
           //     carrier = new CompositeClauseCarrier(List.of(head.right.generateClauses(head.left),
           //             new ClauseList(tail)));
           // } catch (UnificationFailureException e) {
           //     carrier = new CompositeClauseCarrier(List.of(head.left.generateClauses(head.right),
           //             new ClauseList(tail)));
           // }
           // return next();
        }
    }

    @Override
    public boolean hasNext() {
        return carrier.hasNext();
    }

    @Override
    public Map<String, Term> next() {
        if(!carrier.hasNext()) {
            return null;
        }
        try {
            return RecursiveUnify(carrier.next());
        } catch (UnificationFailureException e) {
            return next();
        }
    }
}
