package Prolog.Unification.UnificationClauses.ListReexecution;
import java.util.Iterator;
import java.util.Stack;


public class SolutionFinder implements Iterator<int[]> {

    public SolutionFinder(int sum, int dimension) {
        if(dimension == 1) {
            solver = new OneDimensionalSolver(sum);
        }
        else if(dimension > 2) {
            solver = new MultiDimensionalMutlilinearSolver(dimension, sum);
        } else {
            solver = new TwoDimensionalMultilinearSolver(sum);
        }
    }

    private final MultiLinearSolver solver;
    private int value;
    private int sumMax;

    @Override
    public boolean hasNext() {
        return solver.hasNext();
    }

    @Override
    public int[] next() {
        try {
            Stack<Integer> out = solver.next();
            int[] put = new int[out.size()];
            for (int i = 0; i < put.length; i++) {
                put[i] = out.pop();
            }
            return put;
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}
