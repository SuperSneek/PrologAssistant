package Prolog.Unification.UnificationClauses.ListReexecution;

import java.util.Stack;

public interface MultiLinearSolver {
    public Stack<Integer> next() throws IllegalAccessException;
    boolean hasNext();
}
