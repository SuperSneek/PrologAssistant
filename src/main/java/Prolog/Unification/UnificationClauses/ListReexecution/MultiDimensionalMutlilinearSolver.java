package Prolog.Unification.UnificationClauses.ListReexecution;

import java.util.Stack;

public class MultiDimensionalMutlilinearSolver implements MultiLinearSolver{

    private MultiLinearSolver reducedProblemSolver;

    private int value = 0;
    private int dimension;
    private final int sum;

    public MultiDimensionalMutlilinearSolver(int dimension, int sum) {
        this.sum = sum;
        this.dimension = dimension;
        replaceProblemSolvers(sum-value);
    }

    private void replaceProblemSolvers(int remaining) {
        if(dimension - 1 <= 2) {
            reducedProblemSolver = new TwoDimensionalMultilinearSolver(remaining);
        } else {
            reducedProblemSolver = new MultiDimensionalMutlilinearSolver(dimension - 1, remaining);
        }
    }

    @Override
    public boolean hasNext() {
        return value < sum || reducedProblemSolver.hasNext();
    }

    @Override
    public Stack<Integer> next() throws IllegalAccessException {
        if(reducedProblemSolver.hasNext()) {
            Stack<Integer> out = reducedProblemSolver.next();
            out.push(value);
            return out;
        } else if(value < sum) {
            value++;
            replaceProblemSolvers(sum - value);
            return next();
        }
        return null;
    }
}
