package Prolog.Unification.UnificationClauses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BufferedSolution {

    private final LinkedList<List<UnificationClause>> buffer = new LinkedList<>();


    public BufferedSolution(int size) {
        for (int i = 0; i < size; i++) {
            buffer.add(null);
        }
    }

    public List<UnificationClause> getList(int index) {
        return buffer.get(index);
    }

    public void setList(int index, List<UnificationClause> newL) {
        buffer.set(index, newL);
    }

}
