package Prolog.Unification.UnificationClauses.ListReexecution;

import java.util.Stack;

public class OneDimensionalSolver implements MultiLinearSolver{

    private boolean dirty =false;

    private int sum;

    public OneDimensionalSolver(int sum) {
        this.sum = sum;
    }

    @Override
    public Stack<Integer> next() throws IllegalAccessException {
        dirty = true;
        Stack<Integer> out = new Stack<>();
        out.push(sum);
        return out;
    }

    @Override
    public boolean hasNext() {
        return !dirty;
    }
}
