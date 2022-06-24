package Prolog.Unification.UnificationClauses.ListReexecution;

import lombok.AllArgsConstructor;

import java.util.Iterator;

public class SolutionFinder implements Iterator<int[]> {

    private final int length;
    private final int value;
    private final int index;


    public SolutionFinder(int length,
            int value,
            int index, int sumMax) {
        this.length=length;
        this.value=value;
        this.index=index;
        this.sumMax = sumMax;
        if(length > 0) {
            values = new int[length];
            values[index] = value;
        } else {
            done = true;
        }

    }
    private final int sumMax;
    private int[] values;
    private boolean done = false;
    private boolean dirty = true;
    private int[] bufferedSolution;

    @Override
    public boolean hasNext() {
        if(done && dirty) {
            return false;
        } else {
            if(dirty) {
                bufferedSolution = next();
                dirty = false;
                return !done;
            }
        }
        return true;
    }

    @Override
    public int[] next() {
        if(!dirty) {
            dirty = true;
            return bufferedSolution;
        }
        int[] solution = values.clone();
        int sum = 0;
        boolean changed = false;
        for (int i = 0; i < solution.length - 1; i++) {
            sum += solution[i];
            if(i == index) {
                continue;
            }
            if(solution[i] < sumMax - sum && !changed) {
                values[i]++;
                changed = true;
            }
        }
        if(!changed) {
            done = true;
        }
        solution[solution.length - 1] = sumMax - sum;
        return solution;
    }

    private int[] calculateSolutionWithIndex() throws IllegalArgumentException {
        int[] solution = values.clone();
        int sum = 0;
        boolean changed = false;
        for (int i = 0; i < solution.length - 1; i++) {
            sum += solution[i];
            if(i == index) {
                continue;
            }
            if(solution[i] < sumMax - sum && !changed) {
                values[i]++;
                changed = true;
            }
        }
        if(!changed) {
            done = true;
        }
        for (int j = 0; j < length; j++) {
            //Find first non index instance where sum of solution is equal to values
            if(j == index) {
                continue;
            }
            if()
        }
        if(sumMax != sum) {
            throw new IllegalArgumentException("Solution is impossible");
        }
        return solution;
    }
}
