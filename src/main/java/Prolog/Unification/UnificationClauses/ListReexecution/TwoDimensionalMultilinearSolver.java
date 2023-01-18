package Prolog.Unification.UnificationClauses.ListReexecution;

import java.util.Stack;

/**
 * This class finds all splits of a two element list
 */
public class TwoDimensionalMultilinearSolver implements MultiLinearSolver {

    public TwoDimensionalMultilinearSolver(int sum) {
        this.sum = sum;
    }

    int value;
    int sum;

    @Override
    public Stack<Integer> next() throws IllegalAccessException {
        if(!hasNext()) {
            throw new IllegalAccessException("This Solver is at its wits end");
        }
        Stack<Integer> out = new Stack<>();
        out.push(sum - value);
        out.push(value);
        value++;
        return out;
    }

    @Override
    public boolean hasNext() {
        return value <= sum;
    }
}
