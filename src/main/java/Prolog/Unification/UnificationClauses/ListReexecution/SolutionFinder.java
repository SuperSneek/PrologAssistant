package Prolog.Unification.UnificationClauses.ListReexecution;

import lombok.AllArgsConstructor;

import java.util.Arrays;
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
            maxValues = new int[length];
            minValues = new int[length];
            maxValues[index] = value;
            values = new int[length];
        } else {
            done = true;
        }

    }
    private final int sumMax;
    private int[] minValues;
    private int[] maxValues;

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
                bufferedSolution = calculateSolutionWithIndex();
                dirty = false;
                return true;
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
        return calculateSolutionWithIndex();
    }

    private boolean tryChangeMaxValues() {
        boolean changed = false;

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
            if(solution[i] < sumMax && !changed) {
                values[i]++;
                maxValues[i] = Math.max(maxValues[i], values[i]);
                changed = true;
            } else if(solution[i] > 0 && !changed) {
                values[i]--;
                maxValues[i] = Math.max(minValues[i], values[i]);
                changed = true;
            }
        }
        if(!changed) {
            done = true;
        }
        solution[solution.length - 1] = sumMax - sum;
        return solution;
    }
}
