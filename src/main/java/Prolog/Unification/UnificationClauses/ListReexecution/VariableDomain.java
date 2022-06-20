package Prolog.Unification.UnificationClauses.ListReexecution;

import Prolog.PList;
import Prolog.Unification.UnificationClauses.SingleClause;
import Prolog.Unification.UnificationClauses.UnificationClause;
import Prolog.Unification.UnificationClauses.UnificationClauseCarrier;
import Prolog.Variable;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VariableDomain extends UnificationClauseCarrier {

    public VariableDomain(List<Variable> variables, PList list) {
        this.variables = variables;
        this.list = list;
        variableLengths = new int[list.length()];
        size = variables.size();
        listLength = list.length();
        finders = new SolutionFinder[variables.size()];
        for (int i = 0; i < finders.length; i++) {
            finders[i] = new SolutionFinder(variables.size(), 0, i, listLength);
        }
    }

    public VariableDomain merge(VariableDomain other) {
        variables = Stream.concat(variables.stream(), other.variables.stream())
                .collect(Collectors.toList());
        list = list.concat(other.list);
        finders = new SolutionFinder[variables.size()];
        size = variables.size();
        listLength = list.length();
        for (int i = 0; i < finders.length; i++) {
            finders[i] = new SolutionFinder(size, 0, i, listLength);
        }
        return this;
    }

    int[] variableLengths;

    private PList list;

    private int size;
    private int listLength;

    private List<Variable> variables;

    SolutionFinder[] finders ;

    @Override
    public boolean hasNext() {

        for (SolutionFinder finder:
             finders) {
            if(finder.hasNext()) {
                return true;
            }
        }

        for (int listLength : variableLengths) {
            if (listLength < variableLengths.length) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<UnificationClause> next() {
        //See if we can generate a solution without changing a variable length
        for (int i = 0; i < size; i++) {
            if(finders[i].hasNext()) {
                return generateSolution(finders[i].next());
            }
        }

        for (int i = 0; i < size; i++) {
            if(variableLengths[i] < listLength) {
                variableLengths[i]++;
                finders[i] = new SolutionFinder(variableLengths.length, variableLengths[i], i, listLength);
                return next();
            }
        }
        return null;
    }

    public List<UnificationClause> generateSolution(int[] solution) {
        List<UnificationClause> out = new ArrayList<>();
        PList outList = list;
        for (int i = 0; i < solution.length; i++) {
            PList piece = outList.take(solution[i]);
            outList = outList.drop(solution[i]);
            out.add(new UnificationClause(variables.get(i), piece));
        }
        return out;
    }
}
